package com.example.demo.controller;

import com.example.demo.entity.Content;
import com.example.demo.service.IContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/contents")
public class ContentController {

    private final IContentService contentService;

    @GetMapping
    public ResponseEntity<List<Content>> getAllContents() {
        List<Content> contents = contentService.getAllContents();
        return ResponseEntity.ok(contents);
    }

    @PostMapping("/create")
    public ResponseEntity<Content> addContent(@RequestBody Content content) {
        Content newContent = contentService.addContent(content);
        return ResponseEntity.ok(newContent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Content> updateContent(@PathVariable Long id, @RequestBody Content content) {
        Content updatedContent = contentService.updateContent(id, content);
        return ResponseEntity.ok(updatedContent);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Content> approveContent(@PathVariable Long id) {
        Content approvedContent = contentService.approveContent(id);
        return ResponseEntity.ok(approvedContent);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<Content> rejectContent(@PathVariable Long id) {
        Content rejectedContent = contentService.rejectContent(id);
        return ResponseEntity.ok(rejectedContent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
        return ResponseEntity.noContent().build();
    }
}
