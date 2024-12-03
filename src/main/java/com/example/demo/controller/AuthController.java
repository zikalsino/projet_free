package com.example.demo.controller;

import com.example.demo.Dto.ResetPasswordConfirmRequest;
import com.example.demo.Dto.ResetPasswordRequest;
import com.example.demo.entity.AuthenticationResponse;
import com.example.demo.entity.User;
import com.example.demo.security.AuthenticationRequest;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.Impl.EmailService;
import com.example.demo.service.Impl.UserService;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Data
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class AuthController {

   final private AuthenticationService authenticationService;
   final private UserService userService;
   final private PasswordEncoder passwordEncoder;
   final EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            // Authentification et génération du token JWT
            String jwt = authenticationService.authenticate(authenticationRequest);

            // Récupérer l'utilisateur authentifié
            Optional<User> user = userService.findByEmail(authenticationRequest.getEmail());

            // Retourner le JWT et le rôle de l'utilisateur
            return ResponseEntity.ok(new AuthenticationResponse(jwt, user.get().getRole()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        System.out.println("bonjour");
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/request-reset-password")
    public ResponseEntity<String> requestResetPassword(@RequestBody ResetPasswordRequest request) {
        Optional<User> userOptional = userService.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Email not found");
        }

        User user = userOptional.get();
        user.generateResetToken();
        userService.saveUser(user);

        String resetUrl = "http://localhost:8080/reset-password?token=" + user.getResetToken();
        emailService.sendEmail(user.getEmail(), "Password Reset", "Click the link to reset your password: " + resetUrl);

        return ResponseEntity.ok("Reset email sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordConfirmRequest request) {
        Optional<User> userOptional = userService.findByResetToken(request.getCode());
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }

        User user = userOptional.get();
        user.setPassword(request.getNewPassword()); // TODO: Encode password before saving
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        userService.saveUser(user);

        return ResponseEntity.ok("Password reset successful");
    }


}
