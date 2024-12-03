package com.example.demo.service;

import com.example.demo.entity.Statistics;
import com.example.demo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public interface IAdmiService {


    public User createUser(User user) ;
    public User assignRoleToUser(String role, Long userId);

    public Statistics getStatistics();

    public void UpdateUser(Long userId, User userDetails) ;

    public void DeleteUser(Long userId) ;


    public void ManagePermissions(Long userId, List<String> permissions) ;

    public boolean isValidPermission(String permission) ;



    public void administrerContenu(Long contenuId, String action) ;
}