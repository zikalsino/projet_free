package com.example.demo.service;

import com.example.demo.entity.Candidate;
import com.example.demo.entity.Recruiter;
import com.example.demo.entity.User;

import java.util.List;
import java.util.Optional;



public interface IUserService {



    public Optional<User> findByResetToken(String resetToken);
    public Recruiter registerRecruiter(Recruiter Recruiter);
    public Candidate registerCandidate(Candidate candidate);

    public User createUser(User user) ;
    public boolean existsByEmail(String email);

    public Optional<User> findByEmail(String email);


    public List<User> findAllUsers();
    public List<User> findAll() ;




    public void deleteUser(Long id) ;


    public Optional<User> findById(Long id) ;

     public User updateUser(Long id, User user) throws Exception;



}

