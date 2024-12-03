package com.example.demo.Dto;

import lombok.Data;

@Data
public class ResetPasswordConfirmRequest {
    private String code;
    private String newPassword;

    // Getters et setters
}
