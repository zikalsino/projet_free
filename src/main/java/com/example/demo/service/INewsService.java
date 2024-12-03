package com.example.demo.service;

import com.example.demo.entity.News;
import java.util.List;

public interface INewsService {
    News createNews(News news);
    News updateNews(Long id, News news);
    void deleteNews(Long id);
    List<News> getAllNews();
    News getNewsById(Long id);
}
