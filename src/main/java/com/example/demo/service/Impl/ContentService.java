package com.example.demo.service.Impl;

import com.example.demo.Repository.ContentRepository;
import com.example.demo.entity.Content;
import com.example.demo.service.IContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ContentService implements IContentService {

    private final ContentRepository contentRepository;

    @Override
    public List<Content> getAllContents() {
        return contentRepository.findAll();
    }

    @Override
    public Content addContent(Content content) {
        if (Boolean.TRUE.equals(content.getIsDeleted())) {
            throw new IllegalArgumentException("Le contenu ne peut pas être marqué comme supprimé lors de sa création.");
        }

        content.setApprouve(false); // Non approuvé par défaut
        content.setRejete(false);   // Non rejeté par défaut

        if (content.getType() == null || content.getType().isEmpty()) {
            throw new IllegalArgumentException("Le type de contenu est requis.");
        }

        return contentRepository.save(content);
    }

    @Override
    public Content updateContent(Long id, Content contentDetails) {
        Optional<Content> existingContent = contentRepository.findById(id);
        if (existingContent.isPresent()) {
            Content content = existingContent.get();
            content.setTitre(contentDetails.getTitre());
            content.setDescription(contentDetails.getDescription());
            content.setApprouve(contentDetails.isApprouve());
            content.setRejete(contentDetails.isRejete());
            content.setRecruter(contentDetails.getRecruter());
            content.setType(contentDetails.getType());
            content.setContent(contentDetails.getContent());
            content.setIsDeleted(contentDetails.getIsDeleted());
            return contentRepository.save(content); // Cette ligne sauvegarde l'entité mise à jour
        }
        return null;
    }

    public Content approveContent(Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Contenu introuvable"));

        content.setApprouve(true);
        content.setRejete(false);
        return contentRepository.save(content);
    }

    public Content rejectContent(Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Contenu introuvable"));

        content.setApprouve(false);
        content.setRejete(true);
        return contentRepository.save(content);
    }

    public void deleteContent(Long contentId) {
        contentRepository.deleteById(contentId);
    }
}
