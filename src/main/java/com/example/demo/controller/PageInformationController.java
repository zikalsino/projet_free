package com.example.demo.controller;

import com.example.demo.entity.PageInformation;

import com.example.demo.service.Impl.PageInformationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages")
public class PageInformationController {

    private final PageInformationService service;

    public PageInformationController(PageInformationService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public List<PageInformation> getAllPages() {
        return service.getAllPages();
    }

    @GetMapping("/{id}")
    public PageInformation getPageById(@PathVariable Long id) {
        return service.getPageById(id);
    }

    @PostMapping("/create")
    public PageInformation createPage(@RequestBody PageInformation page) {
        return service.createPage(page);
    }

    @PutMapping("/{id}")
    public PageInformation updatePage(@PathVariable Long id, @RequestBody PageInformation page) {
        return service.updatePage(id, page);
    }

    @DeleteMapping("/{id}")
    public void deletePage(@PathVariable Long id) {
        service.deletePage(id);
    }
}
