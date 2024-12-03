package com.example.demo.controller;

import com.example.demo.entity.Candidate;
import com.example.demo.service.Impl.CandidateService;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/file")
public class FileStorageController {


    private final CandidateService candidateService;


    // Récupérer les détails d'un candidat
    @GetMapping("/{fileName}")
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable String fileName) {
        String contentType = candidateService.getFile(fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, STR."attachment; filename=\{fileName}");
        try {
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(this.candidateService.getResource(fileName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }





//    // Suivre l'état d'une candidature
//
//    @GetMapping("/candidature/{candidatureId}/status")
//    public ResponseEntity<String> suivreCandidature(@PathVariable Long candidatureId) {
//        String status = candidateService.suivreCandidature(candidatureId);
//        return ResponseEntity.ok(status);
//    }
}