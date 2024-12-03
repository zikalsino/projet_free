package com.example.demo.controller;

import com.example.demo.entity.Notification;
import com.example.demo.service.Impl.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/notify/{candidateId}")
    public ResponseEntity<String> notifyCandidate(
            @PathVariable Long candidateId,
            @RequestBody String message) {

        notificationService.sendNotification(candidateId, message);
        return ResponseEntity.ok("Notification envoyée avec succès !");
    }

    @GetMapping("/{candidateId}")
    public ResponseEntity<List<Notification>> getUnreadNotifications(
            @PathVariable Long candidateId) {

        List<Notification> notifications = notificationService.getUnreadNotifications(candidateId);
        return ResponseEntity.ok(notifications);
    }

//    @PostMapping("/{candidateId}/mark-as-read")
//    public ResponseEntity<String> markNotificationsAsRead(
//            @PathVariable Long candidateId) {
//
//        notificationService.markNotificationsAsRead(candidateId);
//        return ResponseEntity.ok("Toutes les notifications ont été marquées comme lues !");
//    }
    // Dans le NotificationController
    @PutMapping("/notifications/mark-as-read/{notificationId}")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable Long notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.ok("Notification marquée comme lue !");
    }

}
