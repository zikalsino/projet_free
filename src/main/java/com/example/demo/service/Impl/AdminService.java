package com.example.demo.service.Impl;

import com.example.demo.Handlers.ResourceNotFoundException;
import com.example.demo.Repository.ContentRepository;
import com.example.demo.Repository.StatisticsRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.*;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.IAdmiService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.entity.ERole.ADMIN;

@Data
@RequiredArgsConstructor
@Service
public class AdminService implements IAdmiService {


    private final UserRepository userRepository;
private final StatisticsRepository statisticsRepository;

    private  final ContentRepository contentRepository;


    private  final PasswordEncoder passwordEncoder;

    private final UserDetailsImpl userDetails;


    @Transactional
    public User createUser(User user) {
        // Validation des données d'entrée
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("L'email de l'utilisateur ne peut pas être vide");
        }


        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide");
        }

        // Vérification de l'unicité de l'email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà");
        }

        // Encodage du mot de passe

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Attribution d'un rôle par défaut si non spécifié
        if (user.getRole() == null) {
            user.setRole(ERole.valueOf("User"));
        }

        // Sauvegarde de l'utilisateur
        return userRepository.save(user);
    }

    @Transactional
    public void UpdateUser(Long userId, User userDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'id : " + userId));

        // Mise à jour des champs modifiables
        if (userDetails.getFirstName() != null && !userDetails.getFirstName().trim().isEmpty()) {
            user.setFirstName(userDetails.getFirstName());
        }
        if (userDetails.getEmail() != null && !userDetails.getEmail().trim().isEmpty()) {
            // Vérification de l'unicité du nouvel email
            if (!user.getEmail().equals(userDetails.getEmail()) &&
                    userRepository.findByEmail(userDetails.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Cet email est déjà utilisé par un autre utilisateur");
            }
            user.setEmail(userDetails.getEmail());
        }
        if (userDetails.getPassword() != null && !userDetails.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        if (userDetails.getRole() != null) {
            user.setRole(userDetails.getRole());
        }

        userRepository.save(user);
    }

    @Transactional
    public void DeleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'id : " + userId));

        // Vérification que l'utilisateur n'est pas le dernier administrateur


        userRepository.delete(user);
    }

    @Transactional
    public void ManagePermissions(Long userId, List<String> permissions) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'id : " + userId));

        // Validation des permissions

        List<String> validPermissions = permissions.stream()
                .filter(this::isValidPermission)
                .collect(Collectors.toList());

        if (validPermissions.size() != permissions.size()) {
            throw new IllegalArgumentException("Certaines permissions fournies ne sont pas valides");
        }

        user.setPermissions(validPermissions);
        userRepository.save(user);
    }

    public boolean isValidPermission(String permission) {
        // Liste des permissions valides
        List<String> validPermissions = List.of("READ", "WRITE", "DELETE", "ADMIN");
        return validPermissions.contains(permission.toUpperCase());
    }
    public User assignRoleToUser(String role, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Convertir la chaîne en énumération
        ERole eRole = ERole.valueOf(role);
        user.setRole(eRole);

        return userRepository.save(user);
    }

    public Report genererRapport() {
        Report rapport = new Report();

        // Génération des statistiques

        long totalUsers = userRepository.count();
        long totalAdmins = userRepository.countByRole(ADMIN);
        long totalContenu = contentRepository.count();
        long contenuApprouve = contentRepository.countByApprouveTrue();

        rapport.setContenu(String.format(
                "Rapport général du système:\n" +
                        "Total des utilisateurs: %d\n" +
                        "Total des administrateurs: %d\n" +
                        "Total du contenu: %d\n" +
                        "Contenu approuvé: %d\n" +
                        "Taux d'approbation du contenu: %.2f%%",
                totalUsers, totalAdmins, totalContenu, contenuApprouve,
                (totalContenu > 0 ? (contenuApprouve * 100.0 / totalContenu) : 0)
        ));

        return rapport;
    }

    @Transactional
    public void administrerContenu(Long contenuId, String action) {
        Content content = contentRepository.findById(contenuId)
                .orElseThrow(() -> new ResourceNotFoundException("Contenu non trouvé avec l'id : " + contenuId));

        switch (action.toLowerCase()) {
            case "approuver":
                if (content.isApprouve()) {
                    throw new IllegalStateException("Le contenu est déjà approuvé");
                }
                content.setApprouve(true);
                content.setRejete(false);
                break;
            case "rejeter":
                if (content.isRejete()) {
                    throw new IllegalStateException("Le contenu est déjà rejeté");
                }
                content.setRejete(true);
                content .setApprouve(false);
                break;
//            case "supprimer":
//                content.Delete(content);
//                return;
            default:
                throw new IllegalArgumentException("Action non reconnue: " + action);
        }

        contentRepository.save(content);
    }

    public Statistics getStatistics() {
        // Logique pour récupérer les statistiques depuis la base de données
        Statistics stats = new Statistics();
        stats.setTotalUsers(statisticsRepository.countUsers());
        stats.setTotalJobOffers(statisticsRepository.countJobOffers());
        stats.setTotalApplications(statisticsRepository.countApplications());
        stats.setActiveRecruiters(statisticsRepository.countActiveRecruiters());

        return stats;
    }
}
