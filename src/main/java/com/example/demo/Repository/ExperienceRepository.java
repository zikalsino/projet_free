package com.example.demo.Repository;

import com.example.demo.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    // Ajoutez des méthodes si nécessaire
}
