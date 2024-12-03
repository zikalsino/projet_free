package com.example.demo.entity;

import com.example.demo.entity.ERole;
import com.example.demo.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "candidates")
public class Candidate extends User {

    private String CvPath;
    private String experience;
    private String skills;


    @PrePersist
    public void prePersist() {
        setRole(ERole.CANDIDATE);
    }
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<Competence> competences;
}
