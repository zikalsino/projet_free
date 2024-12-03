package com.example.demo.service;

import com.example.demo.entity.JobOffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface IJobOfferService {



    public Page<JobOffer> getAllJobOffers(int page, int size);

    public List<JobOffer> getActiveJobOffers() ;
    public Optional<JobOffer> getJobOfferById(Long id) ;

    public JobOffer createJobOffer(JobOffer jobOffer, Long Id);

    public JobOffer updateJobOffer(Long id, JobOffer jobOfferDetails) ;

    public JobOffer saveJobOffer(JobOffer jobOffer,Long Id);
    public void deleteJobOffer(Long id) ;
    public JobOffer publieOffer(Long id, boolean publie);
}