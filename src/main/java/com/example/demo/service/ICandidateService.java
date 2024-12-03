package com.example.demo.service;

import com.example.demo.Repository.CandidateRepository;
import com.example.demo.entity.Application;
import com.example.demo.entity.Candidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;



@Service
public interface ICandidateService {

    List<Candidate> findAllCandidates();
    Candidate updateProfile(Candidate candidate);
    void uploadCV(Long candidateId, String  cvPath);
    Candidate getCandidateById(Long id);


    Application postuler(Long candidateId, Long offerId, MultipartFile cvFile) throws IOException;

    //public List<Candidate> searchCandidats(String cvPath);


}

