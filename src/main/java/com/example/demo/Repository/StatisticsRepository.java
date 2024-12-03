package com.example.demo.Repository;

import com.example.demo.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatisticsRepository extends JpaRepository<Statistics, Integer> {
    @Query("SELECT COUNT(u) FROM User u")
    long countUsers();
    @Query("SELECT COUNT(j) FROM JobOffer j")
    long countJobOffers();

    @Query("SELECT COUNT(a) FROM Application a")
    long countApplications();

    @Query("SELECT COUNT(r) FROM Recruiter r ")
    long countActiveRecruiters();
}
