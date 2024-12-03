package com.example.demo.service;

import com.example.demo.entity.PageInformation;
import java.util.List;

public interface IPageInformationService {
    PageInformation createPage(PageInformation page);
    PageInformation updatePage(Long id, PageInformation page);
    void deletePage(Long id);
    List<PageInformation> getAllPages();
    PageInformation getPageById(Long id);
}
