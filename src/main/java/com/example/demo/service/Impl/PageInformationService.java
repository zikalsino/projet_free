package com.example.demo.service.Impl;

import com.example.demo.entity.PageInformation;
import com.example.demo.Repository.PageInformationRepository;

import com.example.demo.service.IPageInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PageInformationService implements IPageInformationService {

    private final PageInformationRepository pageInformationRepository;


    @Override
    public PageInformation createPage(PageInformation page) {
        return pageInformationRepository.save(page);
    }

    @Override
    public PageInformation updatePage(Long id, PageInformation page) {
        PageInformation existingPage = pageInformationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Page not found"));
        existingPage.setTitle(page.getTitle());
        existingPage.setContent(page.getContent());
        return pageInformationRepository.save(existingPage);
    }

    @Override
    public void deletePage(Long id) {
        pageInformationRepository.deleteById(id);
    }

    @Override
    public List<PageInformation> getAllPages() {
        return pageInformationRepository.findAll();
    }

    @Override
    public PageInformation getPageById(Long id) {
        return pageInformationRepository.findById(id).orElseThrow(() -> new RuntimeException("Page not found"));
    }
}
