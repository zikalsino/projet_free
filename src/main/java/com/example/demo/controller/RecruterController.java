package com.example.demo.controller;

import com.example.demo.Repository.NotificationRepository;
import com.example.demo.entity.*;
import com.example.demo.service.Impl.*;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/recruter")
public class RecruterController {

    private final RecruterService recruterService;
    private final JobOfferService jobOfferService;
    private final CandidateService candidateService;
   // private final ApplicationService applicationService;


    @PostMapping("/create/{id}")//ok
    public ResponseEntity<JobOffer> createJobOffer(@PathVariable Long id, @RequestBody JobOffer jobOffer) {
        JobOffer createdJobOffer = jobOfferService.createJobOffer(jobOffer, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJobOffer);
    }
    @GetMapping("/list")
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        List<Candidate> candidates = candidateService.findAllCandidates();
        return ResponseEntity.ok(candidates);
    }





    @PutMapping("/candidatures/{candidatureId}/evaluer")
    public ResponseEntity<Void> evaluerCandidature(@PathVariable Long candidatureId, @RequestBody String evaluation) {
        recruterService.evaluerCandidature(candidatureId, evaluation);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidate(@PathVariable Long id) {
        Candidate candidate = candidateService.getCandidateById(id);
        return ResponseEntity.ok(candidate);
    }



    @GetMapping("/active")//ok
    public List<JobOffer> getActiveJobOffers() {
        return jobOfferService.getActiveJobOffers();
    }




    @PutMapping("/{id}")//ok
    public ResponseEntity<JobOffer> updateJobOffer(@PathVariable Long id, @RequestBody JobOffer jobOfferDetails) {
        JobOffer updatedJobOffer = jobOfferService.updateJobOffer(id, jobOfferDetails);
        return ResponseEntity.ok(updatedJobOffer);
    }

    @DeleteMapping("/{id}")//ok
    public ResponseEntity<?> deleteJobOffer(@PathVariable Long id) {
        jobOfferService.deleteJobOffer(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/jobOffers") //ok
    public ResponseEntity<Page<JobOffer>> getAllJobOffers(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {

        Page<JobOffer> jobOffers = jobOfferService.getAllJobOffers(page, size);

        if (jobOffers.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retourne 204 No Content si aucune offre n'est trouvée
        }

        return ResponseEntity.ok(jobOffers); // Retourne les offres si elles existent
    }






    @GetMapping("/{jobId}")
    public ResponseEntity<JobOffer> getJobOfferById(@PathVariable Long jobId) {
        Optional<JobOffer> jobOffer = jobOfferService.findById(jobId);
        if (jobOffer.isPresent()) {
            return ResponseEntity.ok(jobOffer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Recruiter> updateOffer(@PathVariable Long id, @RequestBody Recruiter recruteur) {
        return ResponseEntity.ok(recruterService.updateOffer(id, recruteur));
    }

    @PostMapping("/changer-statut/{id}")
    public ResponseEntity<Void> changerStatutCandidature(@PathVariable Long id,String newStatus) {
        recruterService.changeStatusCandidature(id, newStatus);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<Candidate>> searchCandidates(
            @RequestParam String skill,
            @RequestParam String experience)
    {
        return ResponseEntity.ok(candidateService.searchCandidates(skill, experience));
    }
    @PutMapping("/offers/{id}") // ok
    public JobOffer publieOffer(@PathVariable Long id, @RequestParam boolean publier) {
        // Mettre à jour l'état de publication de l'offre
        JobOffer jobOffer = jobOfferService.publieOffer(id, publier);

        // Si l'offre est publiée, notifier tous les candidats
        if (publier) {
            recruterService.notifyCandidatesByEmail(jobOffer);
        }

        return jobOffer;
    }


    @GetMapping("/published")
    public ResponseEntity<List<JobOffer>> getPublishedOffers() {
        List<JobOffer> publishedOffers = recruterService.getPublishedOffers();
        return ResponseEntity.ok(publishedOffers);
    }
}





