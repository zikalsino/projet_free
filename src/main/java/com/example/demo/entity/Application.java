package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne

    private User candidate;

    @ManyToOne

    private JobOffer jobOffer;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
     private String cv;
     private String CoverLetter;
    private int score;
    private String notes;



}

