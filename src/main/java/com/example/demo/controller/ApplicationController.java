package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.Impl.ApplicationService;
import com.example.demo.service.Impl.JobOfferService;
import com.example.demo.service.Impl.NotificationService;
import com.example.demo.service.Impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final JobOfferService jobOfferService;
    private final UserService userService;
    private final NotificationService notificationService;


    @PostMapping("/apply")
    public ResponseEntity<String> applyForJob(
            @RequestParam("jobOfferId") Long jobOfferId,
            @RequestParam("cv") MultipartFile cvFile,
            @RequestParam("candidateId") Long candidateId,
    @RequestParam("coverLetter") String CoverLetter)
    {
        applicationService.applyForJob(jobOfferId, candidateId, cvFile,CoverLetter);
        return ResponseEntity.ok("Application soumise avec succès !");
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<Application>> getApplicationsByCandidate(@PathVariable Long candidateId) {
        Optional<User> candidateOptional = userService.findById(candidateId);
        if (candidateOptional.isPresent()) {
            User candidate = candidateOptional.get();
            List<Application> applications = applicationService.getApplicationsByCandidate(candidate);
            return new ResponseEntity<>(applications, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/job-offer/{jobOfferId}")
    public ResponseEntity<List<Application>> getApplicationsForJob(@PathVariable Long jobOfferId) {
        JobOffer jobOffer = jobOfferService.getJobOfferById(jobOfferId).orElseThrow();
        return new ResponseEntity<>(applicationService.getApplicationsForJob(jobOffer), HttpStatus.OK);
    }
    @GetMapping("/list-app")
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }
    @PostMapping("/offres/candidatures")
    public ResponseEntity<List<Application>> visualiserCandidatures(@RequestBody JobOffer jobOffer) {
        System.out.println("Reçu: " + jobOffer);
        if (jobOffer.getId() == null) {
            throw new IllegalArgumentException("ID manquant dans l'objet JobOffer.");
        }
        List<Application> applications = applicationService.getApplicationsForJob(jobOffer);
        return ResponseEntity.ok(applications);
    }



    @PostMapping("/evaluate/{applicationId}")
    public ResponseEntity<Application> evaluateApplication(
            @PathVariable Long applicationId,
            @RequestParam int score,
            @RequestParam String notes
    ) {
        return ResponseEntity.ok(applicationService.evaluateApplication(applicationId, score, notes));
    }

    @PatchMapping("/status/{applicationId}")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam ApplicationStatus status
    ) {
        return ResponseEntity.ok(applicationService.updateApplicationStatus(applicationId, status));
    }

    @PostMapping("/notify/{candidateId}")
    public ResponseEntity<String> notifyCandidate(
            @PathVariable Long candidateId,
            @RequestBody String message) {

        notificationService.sendNotification(candidateId, message);
        return ResponseEntity.ok("Notification envoyée avec succès !");
    }



}
