package com.example.demo.Repository;

import com.example.demo.entity.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    // Ajoutez des méthodes si nécessaire
}
