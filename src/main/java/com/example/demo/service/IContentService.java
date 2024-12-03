package com.example.demo.service;

import com.example.demo.entity.Content;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IContentService {
    public List<Content> getAllContents() ;

    public Content approveContent(Long contentId) ;

    public Content rejectContent(Long contentId) ;

    public void deleteContent(Long contentId) ;
    Content addContent(Content content);
    Content updateContent(Long id, Content content);
}
