package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "job_offers")
public class JobOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String requirements;

    @Column(nullable = false)
    private boolean isActive;


    @ManyToOne
    @JoinColumn(name = "IdPost")
    private User postedBy;

    @Column(nullable = false)
    private boolean publier;

    private String keyword;


    public static void setRecruter(Recruiter recruiter) {


    }


}


