package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long candidateId; // ID du candidat concerné
    private String message;
    private boolean isRead = false; // Permet de marquer les notifications lues/non lues

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();



    public void setCreatedAt(LocalDateTime now) {

    }
}
