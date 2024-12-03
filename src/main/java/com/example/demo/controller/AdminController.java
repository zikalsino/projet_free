package com.example.demo.controller;

import com.example.demo.Handlers.ResourceNotFoundException;
import com.example.demo.Handlers.UnauthorizedOperationException;
import com.example.demo.Handlers.UserNotFoundException;
import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.*;
import com.example.demo.service.Impl.AdminService;
import com.example.demo.service.Impl.ContentService;
import com.example.demo.service.Impl.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;
    private final ContentService contentService;



    @PostMapping("/users")//ok
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        try {
            User newUser = adminService.createUser(user);
            return ResponseEntity.ok(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/Update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            // Trouver l'utilisateur par ID
            User existingUser = userService.findById(id).orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé"));

            // Mettre à jour les champs nécessaires
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setRole(updatedUser.getRole());

            // Enregistrer les modifications
            userService.save(existingUser);

            return ResponseEntity.ok().body(existingUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> DeleteUser(@PathVariable Long userId) {
        try {
            adminService.DeleteUser(userId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/users/{userId}/permissions")
    public ResponseEntity<?> ManagePermissions(@PathVariable Long userId, @RequestBody List<String> permissions) {
        try {
            adminService.ManagePermissions(userId, permissions);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable ERole role) {
        return ResponseEntity.ok(userService.findByRole(role));
    }

    @GetMapping("/rapport")//ok
    public ResponseEntity<Statistics> getStatistics() {
        Statistics stats = adminService.getStatistics();
        return ResponseEntity.ok(stats);
    }







    @PostMapping("/candidates")
    public ResponseEntity<?> registerCandidate(@RequestBody @Valid Candidate candidate) {
        try {
            Candidate savedCandidate = userService.registerCandidate(candidate);
            return ResponseEntity.ok(savedCandidate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PostMapping("/recruiters")
    public ResponseEntity<?> registerRecruiter(@RequestBody Recruiter recruiter) {
        try {
            Recruiter savedRecruiter = userService.registerRecruiter(recruiter);
            return ResponseEntity.ok(savedRecruiter);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/users")//ok
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }




    @PostMapping("/manage")
    public ResponseEntity<?> administrerContenu(@PathVariable Long contenuId, @RequestParam String action) {
        try {
            adminService.administrerContenu(contenuId, action);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/assignRole")
    public ResponseEntity<?> assignRoleToUser(@RequestParam Long userId, @RequestParam String role) {
        try {
            // Convertir la chaîne de caractères en type énuméré ERole
            ERole eRole = ERole.valueOf(role.toUpperCase());

            // Appel du service pour assigner le rôle à l'utilisateur
            User updatedUser = adminService.assignRoleToUser(role, userId);

            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            // Gestion d'une valeur de rôle invalide
            return ResponseEntity.badRequest().body("Rôle invalide : " + role);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Une erreur est survenue : " + e.getMessage());
        }
    }
}

