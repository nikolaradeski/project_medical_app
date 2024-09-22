package com.example.history.controller;

import com.example.history.service.MedicalHistoryServiceImplementation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/history")
public class MedicalHistoryController {
    private final MedicalHistoryServiceImplementation medicalHistoryService;

    public MedicalHistoryController(MedicalHistoryServiceImplementation medicalHistoryService) {
        this.medicalHistoryService = medicalHistoryService;
    }

    @GetMapping("/{patientId}")
    public String getMedicalHistory(@PathVariable Long patientId)
    {
        return medicalHistoryService.printMedicalCondition(patientId);
    }
}
