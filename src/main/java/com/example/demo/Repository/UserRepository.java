package com.example.demo.Repository;// Importation des bibliothèques nécessaires
import com.example.demo.entity.ERole;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Méthode pour trouver un utilisateur par son email

    Optional<User> findByEmail(String email);
    Optional<User> findUserByEmail(String email);
    Optional<User> findByResetToken(String resetToken);

    // Méthode pour vérifier l'existence d'un utilisateur par email

    boolean existsByEmail(String email);
    long countByRole(ERole role);

    List<User> findByRole(ERole role);


}
