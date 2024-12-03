package com.example.demo.service.Impl;

import com.example.demo.Repository.*;
import com.example.demo.entity.*;

import com.example.demo.service.IRecruterService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@RequiredArgsConstructor
@Service
public class RecruterService implements IRecruterService {


    private final RecruiterRepository recruiterRepository;
    private  final JobOfferRepository jobOfferRepository;
    private  final ApplicationRepository applicationRepository;
    private  final CandidateRepository candidateRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;




    @Override
    public JobOffer createJobOffer(Long recruterId, JobOffer jobOffer) {
        Recruiter recruter = recruiterRepository.findById(recruterId)
                .orElseThrow(() -> new RuntimeException("Recruteur non trouvé"));
        JobOffer.setRecruter(recruter);
        return jobOfferRepository.save(jobOffer);
    }
    @Override
    public Recruiter updateOffer(Long id, Recruiter updatedRecruiter) {
        Recruiter recruteur = recruiterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recruteur non trouvé"));
        recruteur.setEntreprise(updatedRecruiter.getEntreprise());
        return recruiterRepository.save(recruteur);
    }
    @Override
    public List<Application> visualiserCandidatures(JobOffer jobOffer) {
        return applicationRepository.findByJobOffer(jobOffer);
    }

    @Override
    public void evaluerCandidature(Long Id, String evaluation) {
        Application application = applicationRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Candidature non trouvée"));
        //application.setEvaluation(evaluation);
        applicationRepository.save(application);
    }
    @Override
    public void changeStatusCandidature(Long candidatureId, String newStatus) {
        Application application= applicationRepository.findById(candidatureId)
                .orElseThrow(() -> new RuntimeException("Candidature non trouvée"));
        application.setStatus(ApplicationStatus.valueOf(newStatus));
        applicationRepository.save(application);
    }

  public void notifyCandidatesByEmail(JobOffer jobOffer) {
        List<User> candidates = userRepository.findByRole(ERole.valueOf("CANDIDATE"));
        for (User candidate : candidates) {
            try {
                String subject = "Nouvelle offre d'emploi publiée : " + jobOffer.getTitle();
                String text = "Bonjour " + candidate.getFirstName() + " " + candidate.getLastName() + ",\n\n" +
                        "Une nouvelle offre d'emploi a été publiée : " + jobOffer.getTitle() +
                        ".\nConsultez notre site pour plus de détails.\n\nCordialement,\nL'équipe de recrutement.";
                emailService.sendEmail(candidate.getEmail(), subject, text);

                // Ajoutez un délai de 1 seconde entre chaque envoi
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Erreur lors du délai d'attente entre les envois d'e-mails", e);
            }
        }
    }


//    @Override
//    public JobOffer publieOffer(Long id, boolean publier) {
//        return null;
//    }

    @Override
    public List<JobOffer> getPublishedOffers() {
        return jobOfferRepository.findByIsActiveTrue(); // Vérifie si le champ 'published' est vrai
    }




}