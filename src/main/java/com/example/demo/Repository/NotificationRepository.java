package com.example.demo.Repository;

// Importation des bibliothèques nécessaires
import com.example.demo.entity.Notification;
import com.example.demo.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

   // List<Notification> findByUser(User user);

    List<Notification> findByCandidateIdAndIsReadFalse(Long candidateId);

   // List<Notification> findByCandidateIdAndReadFalse(Long candidateId);
//    List<Notification> findByUserAndStatus(User user, boolean status);
}

