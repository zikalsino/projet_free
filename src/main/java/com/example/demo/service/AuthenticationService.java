package com.example.demo.service;

import com.example.demo.security.AuthenticationRequest;
import com.example.demo.security.JwtTokenUtil;
import com.example.demo.security.UserDetailsImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Data
@Service
public class AuthenticationService {


    private final AuthenticationManager authenticationManager;


    private final UserDetailsService userDetailsImpl;


    private final     PasswordEncoder passwordEncoder;


    private final JwtTokenUtil jwtTokenUtil;

    public String authenticate(AuthenticationRequest authRequest) throws Exception {
        try {
            // Vérifier si l'utilisateur existe
            UserDetails userDetails = userDetailsImpl.loadUserByUsername(authRequest.getEmail());
            System.out.println("USER");
            System.out.println(userDetails);
            // Comparer les mots de passe
            if (passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())) {

                // Créer le jeton JWT

                return jwtTokenUtil.generateToken(userDetails);


            } else {
                throw new Exception("Utilisateur ou mot de passe incorrect");
            }
        } catch (AuthenticationException e) {
            throw new Exception("Échec de l'authentification", e);
        }
    }
}
