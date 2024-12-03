package com.example.demo.service;

import com.example.demo.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;



public interface IApplicationService  {



    public void applyForJob(Long jobOfferId, Long candidateId, MultipartFile cvFile,String coverLetter);

    //public void markNotificationAsRead(Long notificationId);
    //public List<Notification> getUnreadNotifications(Long candidateId);
    public List<Application> getApplicationsByCandidate(User candidate) ;

    public List<Application> getApplicationsForJob(JobOffer jobOffer) ;
    public Application evaluateApplication(Long id, int score, String notes);
    //public void notifyCandidate(Long id, String type) ;
    public List<Application> getAllApplications();
    public Application updateApplicationStatus(Long id, ApplicationStatus status) ;
}

