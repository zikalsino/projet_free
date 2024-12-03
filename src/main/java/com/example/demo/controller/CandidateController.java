package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.Impl.ApplicationService;
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
@RequestMapping("/api/candidates")
public class CandidateController {


    private final CandidateService candidateService;
    private final ApplicationService applicationService;






    @PostMapping("/apply")
    public ResponseEntity<String> applyForJob(
            @RequestParam("jobOfferId") Long jobOfferId,
            @RequestParam("cv") MultipartFile cvFile,
            @RequestParam("candidateId") Long candidateId,
            @RequestParam("CoverLetter") String  CoverLetter) {
        applicationService.applyForJob(jobOfferId, candidateId, cvFile,CoverLetter);
        return ResponseEntity.ok("Application soumise avec succès !");
    }

}