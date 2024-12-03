package com.example.demo.Repository;


import com.example.demo.entity.Application;
import com.example.demo.entity.ApplicationStatus;
import com.example.demo.entity.JobOffer;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {


    List<Application> findByCandidateId(Long candidateId);


    // Méthode pour trouver les candidatures d'un candidat spécifique

    List<Application> findByCandidate(User candidate);

    // Méthode pour trouver les candidatures pour une offre d'emploi spécifique
     List<Application> findByJobOffer(JobOffer jobOffer);

    // Méthode pour trouver les candidatures par statut
    List<Application> findByStatus(ApplicationStatus status);
   // List<Application> getAllApplications();

}
