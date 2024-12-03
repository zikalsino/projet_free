package com.example.demo.service.Impl;

import com.example.demo.Handlers.ResourceNotFoundException;
import com.example.demo.Repository.RecruiterRepository;
import com.example.demo.entity.JobOffer;
import com.example.demo.entity.Recruiter;
import com.example.demo.entity.User;
import com.example.demo.Repository.JobOfferRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.service.IJobOfferService;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class JobOfferService implements IJobOfferService {


    private  final JobOfferRepository jobOfferRepository;
    private final RecruiterRepository recruiterRepository;

    private  final UserRepository userRepository;

    public Page<JobOffer> getAllJobOffers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return jobOfferRepository.findAll(pageable);
    }
     public List<JobOffer> searchJobOffers(String keyword, String location, String Description) {
        // Rechercher les offres d'emploi en fonction des critères
        return jobOfferRepository.findByKeywordAndLocationAndDescription(keyword, location, Description);
    }


    public List<JobOffer> getActiveJobOffers() {
        return jobOfferRepository.findByIsActiveTrue();
    }

    public Optional<JobOffer> getJobOfferById(Long id) {
        return jobOfferRepository.findById(id);
    }


    public JobOffer createJobOffer(JobOffer jobOffer,Long id) {
        User postedBy = userRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("User not found"));
        jobOffer.setId(null);
        jobOffer.setTitle(jobOffer.getTitle());
        jobOffer.setDescription(jobOffer.getDescription());
        jobOffer.setCompany(jobOffer.getCompany());
        jobOffer.setLocation(jobOffer.getLocation());
        jobOffer.setRequirements(jobOffer.getRequirements());
        jobOffer.setActive(jobOffer.isActive());
        jobOffer.setPostedBy(postedBy);

        return jobOfferRepository.save(jobOffer);
    }

    public JobOffer updateJobOffer(Long id, JobOffer jobOfferDetails) {
        JobOffer jobOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job offer not found"));

        jobOffer.setTitle(jobOfferDetails.getTitle());
        jobOffer.setDescription(jobOfferDetails.getDescription());
        jobOffer.setCompany(jobOfferDetails.getCompany());
        jobOffer.setLocation(jobOfferDetails.getLocation());
        jobOffer.setRequirements(jobOfferDetails.getRequirements());
        jobOffer.setActive(jobOfferDetails.isActive());

        return jobOfferRepository.save(jobOffer);
    }

    public void deleteJobOffer(Long id) {
        JobOffer jobOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job offer not found"));
        jobOfferRepository.delete(jobOffer);
    }

    public JobOffer publieOffer(Long id, boolean publier) {
        JobOffer jobOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job offer not found"));
        jobOffer.setPublier(publier);

       return jobOfferRepository.save(jobOffer);
    }
    public JobOffer saveJobOffer(JobOffer jobOffer, Long Id) {
        Recruiter recruiter = recruiterRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found"));
        jobOffer.setRecruter(recruiter);
        return jobOfferRepository.save(jobOffer);
    }

    public Optional<JobOffer> findById(Long jobId) {

        return jobOfferRepository.findById(jobId);
    }



}