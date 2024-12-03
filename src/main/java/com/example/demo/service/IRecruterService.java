package com.example.demo.service;

import com.example.demo.entity.Application;
import com.example.demo.entity.Candidate;
import com.example.demo.entity.JobOffer;
import com.example.demo.entity.Recruiter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRecruterService {

    JobOffer createJobOffer(Long recruterId, JobOffer offre);

    List<Application> visualiserCandidatures(JobOffer JobOffer);
    void evaluerCandidature(Long candidatureId, String evaluation);
    //void changerStatutCandidature(Long candidatureId, String nouveauStatut);
   // void sendMessage(Long candidatureId, );
    public Recruiter updateOffer(Long id, Recruiter updatedRecruter);
    public void changeStatusCandidature(Long candidatureId, String newStatus);
    //public JobOffer publieOffer(Long id, boolean publier);
    public List<JobOffer> getPublishedOffers();
}

