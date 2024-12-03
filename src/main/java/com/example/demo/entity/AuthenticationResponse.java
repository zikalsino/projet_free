package com.example.demo.entity;

import lombok.*;

import javax.management.relation.Role;

@AllArgsConstructor @NoArgsConstructor
@Data
public class AuthenticationResponse {

    private  String jwt;

   private AuthenticationResponse authenticationResponse;
   private ERole role;


    public AuthenticationResponse(String jwt, ERole role) {
        this.jwt = jwt;
        this.role = role;
    }
}
