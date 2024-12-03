package com.example.demo.controller;

import com.example.demo.entity.Report;

import com.example.demo.service.Impl.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reports")
public class ReportController {


    private final ReportService reportService;

    // Récupérer tous les rapports
    @GetMapping("/list")
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    // Récupérer un rapport par ID
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable long id) {
        Optional<Report> report = reportService.getReportById(id);
        return report.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Créer un nouveau rapport
    @PostMapping("/create")
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        Report createdReport = reportService.createReport(report);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }

    // Mettre à jour un rapport existant
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable long id, @RequestBody Report reportDetails) {
        Report updatedReport = reportService.updateReport(id, reportDetails);
        return updatedReport != null ? ResponseEntity.ok(updatedReport) : ResponseEntity.notFound().build();
    }

    // Supprimer un rapport
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable long id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}
