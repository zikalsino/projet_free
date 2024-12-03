package com.example.demo.service.Impl;

import com.example.demo.entity.News;
import com.example.demo.Repository.NewsRepository;
import com.example.demo.service.INewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsService implements INewsService {

    private final NewsRepository newsRepository;



    @Override
    public News createNews(News news) {
        return newsRepository.save(news);
    }

    @Override
    public News updateNews(Long id, News news) {
        News existingNews = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found"));
        existingNews.setTitle(news.getTitle());
        existingNews.setContent(news.getContent());
        existingNews.setCategory(news.getCategory());
        return newsRepository.save(existingNews);
    }

    @Override
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public News getNewsById(Long id) {
        return newsRepository.findById(id).orElseThrow(() -> new RuntimeException("News not found"));
    }
}
