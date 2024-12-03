package com.example.demo.entity;

import com.example.demo.entity.Candidate;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Competence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String domaine;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private User candidate;


}
