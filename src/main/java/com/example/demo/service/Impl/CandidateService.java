package com.example.demo.service.Impl;

import com.example.demo.Repository.*;
import com.example.demo.entity.*;
import com.example.demo.service.ICandidateService;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CandidateService implements ICandidateService {

    private final ExperienceRepository experienceRepository;
    private final CompetenceRepository competenceRepository;
    private  final CandidateRepository candidateRepository;
    private  final ApplicationRepository applicationRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public List<Candidate> findAllCandidates() {
        return candidateRepository.findAll();
    }





    @Override
    public void uploadCV(Long candidateId, String cvPath) {
        Candidate candidate = getCandidateById(candidateId); // Récupère le candidat par ID
        if (candidate == null) {
            throw new IllegalArgumentException("Candidat introuvable avec l'ID : " + candidateId);
        }
        candidate.setCvPath(cvPath); // Met à jour le chemin du CV avec la valeur fournie
        candidateRepository.save(candidate); // Sauvegarde la mise à jour
    }


    @Override
    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
    }


   @Override
    public Application postuler(Long candidateId, Long offerId, MultipartFile cvFile) throws IOException, java.io.IOException {
        // Save the file to a directory and get its path
        String filePath = saveCvFile(cvFile);
        System.out.println("FILE URL");
        System.out.println(filePath);
        var user = new User();
        user.setId(candidateId);
        var offer = new JobOffer();
        offer.setId(offerId);
        // Create the application with the path to the saved file
        Application application = new Application();
        application.setCandidate(user);
        application.setJobOffer(offer);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setCv(String.valueOf(cvFile));

       applicationRepository.save(application);
       return application;
    }
    final String url="http://localhost:8080/file/";
    final String  uploadDir = "C:\\Users\\hp\\OneDrive - Free Senegal\\Bureau\\Projets\\Safar-main\\CabManagementSystem\\demo\\src\\main\\resources";

    private String saveCvFile(MultipartFile file) throws IOException, java.io.IOException {
        String fileName = STR."\{UUID.randomUUID()}.\{Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1]}";
        System.err.println(STR."FILE EXTENTION \{fileName}");
        File uploadFile = new File(uploadDir, fileName);
        file.transferTo(uploadFile);
        return url + fileName;
    }


    public String getFile(String fileName){
        try {
            File imageFile = new File(uploadDir, fileName);
            if (!imageFile.exists()) {
                return "";
            }
            byte[] imageContent = Files.readAllBytes(imageFile.toPath());
            ByteArrayResource resource = new ByteArrayResource(imageContent);

            // Determine the content type based on the file extension
           return Files.probeContentType(imageFile.toPath());
        } catch (Exception e) {
            return "";
        }
    }
    public ByteArrayResource getResource(String fileName) throws Exception {
        try {
            File imageFile = new File(uploadDir, fileName);
            byte[] imageContent = Files.readAllBytes(imageFile.toPath());
            return new ByteArrayResource(imageContent);
        } catch (Exception e) {
           throw new Exception("File not exist");
        }


    }
    public List<Candidate> searchCandidates(String skill, String experience) {
        return candidateRepository.findBySkillsContainingOrExperienceContaining(skill,experience);
    }

    public Candidate updateProfile(Candidate candidate) {
        candidate.setExperience(candidate.getExperience());
        candidate.setSkills(candidate.getSkills());

        return candidateRepository.save(candidate);
    }

    // Exemple de méthode pour ajouter une expérience
    public Experience addExperience(Long candidateId, Experience experience) {
       User candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé"));

        experience.setCandidate(candidate);
        experience.setPoste(experience.getPoste());
        experience.setEntreprise(experience.getEntreprise());
        experience.setDescription(experience.getDescription());
        experience.setDateDebut(experience.getDateDebut());
        experience.setDateFin(experience.getDateFin());
        return experienceRepository.save(experience);
    }

    // Exemple de méthode pour ajouter une compétence
    public Competence addCompetence(Long candidateId, Competence competence) {
       User candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new IllegalArgumentException("Candidat non trouvé"));

        competence.setCandidate(candidate);
        competence.setDomaine(competence.getDomaine());
        competence.setNom(competence.getNom());
        return competenceRepository.save(competence);
    }

}


