package com.example.demo.controller;

import com.example.demo.entity.Candidate;
import com.example.demo.entity.Competence;
import com.example.demo.entity.Experience;

import com.example.demo.service.Impl.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final CandidateService candidateService;

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getProfile(@PathVariable Long id) {
        Candidate candidate = candidateService.getCandidateById(id);
        return ResponseEntity.ok(candidate);
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<Candidate> updateProfile(@PathVariable Long id, @RequestBody Candidate candidate) {
        candidate.setId(id);
        Candidate updatedCandidate = candidateService.updateProfile(candidate);
        return ResponseEntity.ok(updatedCandidate);
    }

    @PostMapping("/{id}/experiences")
    public ResponseEntity<Experience> addExperience(@PathVariable Long id, @RequestBody Experience experience) {
        Experience addedExperience = candidateService.addExperience(id, experience);
        return ResponseEntity.ok(addedExperience);
    }

    @PostMapping("/{id}/competences")
    public ResponseEntity<Competence> addCompetence(@PathVariable Long id, @RequestBody Competence competence) {
        Competence addedCompetence = candidateService.addCompetence(id, competence);
        return ResponseEntity.ok(addedCompetence);
    }

    @PostMapping("/{id}/cv")
    public ResponseEntity<Void> uploadCV(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        candidateService.uploadCV(id, String.valueOf(file));
        return ResponseEntity.ok().build();
    }
}
