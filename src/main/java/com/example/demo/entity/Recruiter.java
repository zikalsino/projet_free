package com.example.demo.entity;

import com.example.demo.entity.ERole;
import com.example.demo.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "recruiters")
public class Recruiter extends User {
    @Column
    private String entreprise;

    @PrePersist
    public void prePersist() {
        setRole(ERole.RECRUITER);
    }
}