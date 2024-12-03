package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "contenus")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre ne peut pas être vide")
    @Size(max = 200, message = "Le titre ne peut pas dépasser 200 caractères")
    private String titre;

    @NotBlank(message = "La description ne peut pas être vide")
    @Column(length = 1000)
    private String description;

    private boolean approuve;

    private boolean rejete;


    @ManyToOne

    @JoinColumn(name = "recruiter_id")
    private User Recruter;

    private String type; // FAQ, Terms, Privacy, etc.

    @Column(columnDefinition = "TEXT")
    private String content; //
    @Column(name = "is_deleted")
    private Boolean isDeleted;

//    public void Delete(Content content) {
//        Delete = true;
//
//    }
}

