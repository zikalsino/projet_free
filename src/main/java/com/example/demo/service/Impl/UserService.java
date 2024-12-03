package com.example.demo.service.Impl;

import com.example.demo.Handlers.UserNotFoundException;
import com.example.demo.Repository.CandidateRepository;
import com.example.demo.Repository.RecruiterRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.Candidate;
import com.example.demo.entity.ERole;
import com.example.demo.entity.Recruiter;
import com.example.demo.entity.User;
import com.example.demo.service.IUserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Data
@RequiredArgsConstructor
@Service
public class UserService implements IUserService {


   private final UserRepository userRepository;
 private final CandidateRepository candidateRepository;
private final RecruiterRepository recruiterRepository;
    private final PasswordEncoder passwordEncoder;


    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    public Optional<User> findById(Long id) {

        Optional<User> user = userRepository.findById(id);



        return Optional.ofNullable(user.orElseThrow(() -> new UserNotFoundException("Utilisateur avec ID " + id + " non trouvé")));
    }

    public User updateUser(Long id, User userDetails) throws Exception {
        // Récupère l'utilisateur par son id
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception(STR."Utilisateur non trouvé avec l'id : \{id}"));

        // Mets à jour les champs nécessaires
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());

        // Sauvegarde les changements
        return userRepository.save(user);
    }



@Transactional
    public Candidate registerCandidate(Candidate candidate) {
        if (candidate.getEmail() == null || candidate.getFirstName() == null || candidate.getLastName() == null) {
            throw new IllegalArgumentException("Tous les champs obligatoires doivent être remplis");
        }
        return candidateRepository.save(candidate);
    }


    @Override
    public Optional<User> findByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

    @Transactional
    public Recruiter registerRecruiter(Recruiter recruiter) {
        if (userRepository.existsByEmail(recruiter.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }
        // Ici vous pourriez ajouter l'encodage du mot de passe
        return recruiterRepository.save(recruiter);
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }


    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    public List<User> findByRole(ERole role) {
        return userRepository.findByRole(role);
    }



    public void updatePassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);


    }
    public User save(User user) {
        // Si l'utilisateur existe déjà (c'est-à-dire qu'il a un ID), on fait une mise à jour
        if (user.getId() != null) {
            Optional<User> existingUser = userRepository.findById(user.getId());
            if (existingUser.isPresent()) {
                // On met à jour l'utilisateur existant
                User updatedUser = existingUser.get();
                updatedUser.setFirstName(user.getFirstName());
                updatedUser.setLastName(user.getLastName());
                updatedUser.setEmail(user.getEmail());
                updatedUser.setRole(user.getRole());
                return userRepository.save(updatedUser); // Sauvegarde les modifications
            }
        }
        // Si l'utilisateur n'existe pas encore, on en crée un nouveau
        return userRepository.save(user);
    }

}

