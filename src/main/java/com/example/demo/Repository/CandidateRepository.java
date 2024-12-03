package com.example.demo.Repository;

import com.example.demo.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    List<Candidate> findByEmail(String email);


    List<Candidate> findAll();

    List<Candidate> findBySkillsContainingOrExperienceContaining(
           String skill, String experience);
}

