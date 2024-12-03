package com.example.demo.service;

import com.example.demo.Repository.ReportRepository;
import com.example.demo.entity.Report;

import java.util.List;
import java.util.Optional;

public interface IReportService {

    // Méthode pour récupérer tous les rapports
    public List<Report> getAllReports() ;


    // Méthode pour récupérer un rapport par ID
    public Optional<Report> getReportById(long id) ;

    // Méthode pour créer un nouveau rapport
    public Report createReport(Report report) ;

    // Méthode pour mettre à jour un rapport existant
    public Report updateReport(long id, Report reportDetails) ;

    // Méthode pour supprimer un rapport
    public void deleteReport(long id) ;
}
