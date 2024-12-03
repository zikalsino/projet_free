package com.example.demo.Handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class PrimeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;


    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        /*  response.setHeader("Access-Control-Allow-Origin", "*");
        // You can specify the origin instead of '*'
          response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
           response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
          response.setHeader("Access-Control-Expose-Headers", "Authorization, Content-Type");*/
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(JsonViewModel
                .builder()
                .message("Non autorisé : Une authentification est requise")
                //.status(ActionStatus.FAILED).data(null)
                .build()));    }}