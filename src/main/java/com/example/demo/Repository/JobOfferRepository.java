package com.example.demo.Repository;// Importation des bibliothèques nécessaires


import com.example.demo.entity.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    List<JobOffer> findByIsActiveTrue();
    List<JobOffer> findByPostedById(Long Id);
    //List<JobOffer> findByPublishedTrue();


    List<JobOffer> findByKeywordAndLocationAndDescription(String keyword, String location, String description);
    List<JobOffer> findByPublierTrue();
}
