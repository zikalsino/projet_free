package com.example.demo.service.Impl;

import com.example.demo.Repository.ApplicationRepository;
import com.example.demo.Repository.JobOfferRepository;
import com.example.demo.Repository.NotificationRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.*;
import com.example.demo.service.IApplicationService;
import io.jsonwebtoken.io.IOException;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@Service
public class ApplicationService implements IApplicationService {


    private final ApplicationRepository applicationRepository;
    private final JobOfferRepository jobOfferRepository;
    private final JobOfferService jobOfferService;
    private final UserService userService;
    private final NotificationRepository notificationRepository;


    public void applyForJob(Long jobOfferId, Long candidateId, MultipartFile cvFile, String Coverletter) {
        if (jobOfferId == null || candidateId == null || cvFile == null || Coverletter == null) {
            throw new IllegalArgumentException("L'identifiant de l'offre d'emploi, celui du candidat, et le fichier CV sont requis.");
        }

        JobOffer jobOffer = jobOfferService.getJobOfferById(jobOfferId)
                .orElseThrow(() -> new IllegalArgumentException("Offre d'emploi introuvable"));
        User candidate = userService.findById(candidateId)
                .orElseThrow(() -> new IllegalArgumentException("Candidat introuvable"));

        Application application = new Application();
        application.setJobOffer(jobOffer);
        application.setCandidate(candidate);

        // Sauvegarder le fichier CV sur le disque ou dans un service de stockage
        String cvPath=null;
        try {
             cvPath = saveFile(cvFile); // Utilisez une méthode pour sauvegarder et obtenir le chemin du fichier
        } catch (java.io.IOException e) {
            throw new IllegalArgumentException("Erreur Upload cv");
        }
        application.setCv(cvPath);
        application.setCoverLetter(application.getCoverLetter() + Coverletter);
        application.setStatus(ApplicationStatus.APPLIED);

        applicationRepository.save(application);
    }
    final String  uploadDir = "C:\\Users\\hp\\OneDrive - Free Senegal\\Bureau\\Projets\\Safar-main\\CabManagementSystem\\demo\\src\\main\\resources";

    private String saveFile(MultipartFile file) throws IOException, java.io.IOException {
        String fileName = STR."\{UUID.randomUUID()}.\{Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1]}";
        System.err.println(STR."FILE EXTENTION \{fileName}");
        File uploadFile = new File(uploadDir, fileName);
        file.transferTo(uploadFile);
        return url + fileName;
    }
    final String url="http://localhost:8080/file/";
    // Méthode pour sauvegarder le fichier CV
    private String saveCvFile(MultipartFile cvFile) {
        // Logique pour sauvegarder le fichier et retourner le chemin d'accès
        String filePath = uploadDir + cvFile.getOriginalFilename();
        try {
            cvFile.transferTo(new File(filePath)); // Sauvegarde le fichier
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du fichier CV", e);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        return filePath;
    }



    public List<Application> getApplicationsByCandidate(User candidate) {
        return applicationRepository.findByCandidate(candidate);
    }



        public List<Application> getApplicationsForJob(JobOffer jobOffer) {
        return applicationRepository.findByJobOffer(jobOffer);
    }
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }


    public Application applyForJob(Long jobOfferId, MultipartFile cvFile) {
        // Enregistrer le fichier CV et la candidature ici
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new RuntimeException("Job offer not found"));

        Application application = new Application();
        application.setJobOffer(jobOffer);
        application.setCv(cvFile.getOriginalFilename());
        // Logique pour enregistrer le fichier (optionnelle)

        return applicationRepository.save(application);
    }

    public Application updateApplicationStatus(Long id, ApplicationStatus status) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidature non trouvée"));
        application.setStatus(status);
        return applicationRepository.save(application);
    }


    public Application evaluateApplication(Long id, int score, String notes) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidature non trouvée"));
        application.setScore(score);
        application.setNotes(notes);
        return applicationRepository.save(application);
    }


//    public void notifyCandidate(Long id, String type) {
//        Application application = applicationRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Candidature non trouvée"));
//
//        // Logique de notification (envoi de l’email ou autre)
//        switch (type) {
//            case "accept":
//                System.out.println(STR."Notification d'acceptation envoyée à \{application.getCandidate()}");
//                break;
//            case "reject":
//                System.out.println(STR."Notification de rejet envoyée à \{application.getCandidate()}");
//                break;
//            case "requestInfo":
//                System.out.println(STR."Demande d'informations envoyée à \{application.getCandidate()}");
//                break;
//        }
//    }

//    public void notifyCandidate(Long candidateId, String message) {
//        Notification notification = new Notification();
//        notification.setCandidateId(candidateId);
//        notification.setMessage(message);
//        notificationRepository.save(notification);
//    }
//
//    // Marquer une notification comme lue
//    public void markNotificationAsRead(Long notificationId) {
//        Notification notification = notificationRepository.findById(notificationId)
//                .orElseThrow(() -> new RuntimeException("Notification introuvable"));
//        notification.setRead(true);
//        notificationRepository.save(notification);
//    }

//    public List<Notification> getUnreadNotifications(Long candidateId) {
//        if (candidateId == null) {
//            throw new IllegalArgumentException("L'ID du candidat ne peut pas être nul.");
//        }
//
//        return notificationRepository.findByCandidateIdAndIsReadFalse(candidateId);
//    }
}

