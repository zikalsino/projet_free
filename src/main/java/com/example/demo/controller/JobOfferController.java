package com.example.demo.controller;

import com.example.demo.entity.JobOffer;
import com.example.demo.service.Impl.JobOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/job-offers")
public class JobOfferController {

    private final JobOfferService jobOfferService;

    @GetMapping("/search")
    public ResponseEntity<List<JobOffer>> searchJobOffers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String description) {

         List<JobOffer> offer = jobOfferService.searchJobOffers(keyword, location, description);
        return new ResponseEntity<>(offer, HttpStatus.OK);
    }
}
