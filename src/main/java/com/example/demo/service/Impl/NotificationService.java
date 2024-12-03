package com.example.demo.service.Impl;


import com.example.demo.Repository.NotificationRepository;
import com.example.demo.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void sendNotification(Long candidateId, String message) {
        Notification notification = new Notification();
        notification.setCandidateId(candidateId);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
        System.out.println("Notification envoyée au candidat ID: " + candidateId);
    }


    public List<Notification> getUnreadNotifications(Long notificationId) {
        return notificationRepository.findByCandidateIdAndIsReadFalse(notificationId);
    }

    public void markNotificationAsRead(Long notificationId) {
        List<Notification> notifications = notificationRepository.findByCandidateIdAndIsReadFalse(notificationId);
        for (Notification notification : notifications) {
            notification.setRead(true);
        }
        notificationRepository.saveAll(notifications);
    }


}
