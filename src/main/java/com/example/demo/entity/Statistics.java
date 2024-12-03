package com.example.demo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "stats")
public class Statistics {

    private long totalUsers;
    private long totalJobOffers;
    private long totalApplications;
    private long activeRecruiters;
    @Id
    private Long id;




}

