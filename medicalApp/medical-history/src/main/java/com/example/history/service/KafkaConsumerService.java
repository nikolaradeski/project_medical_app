package com.example.history.service;

import com.example.events.AddDiagnosisToMedicalHistory;
import com.example.events.AddPrescriptionToMedicalHistory;
import com.example.events.AddSymptomToMedicalHistory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private final MedicalHistoryServiceImplementation medicalHistoryService;

    public KafkaConsumerService(MedicalHistoryServiceImplementation medicalHistoryService) {
        this.medicalHistoryService = medicalHistoryService;
    }

    @KafkaListener(topics = "symptom-events", groupId = "symptom-group", containerFactory = "symptomKafkaListenerContainerFactory")
    public void listen1(AddSymptomToMedicalHistory event) {
        medicalHistoryService.addSymptomEventListener(event);
    }

    @KafkaListener(topics = "diagnosis-events", groupId = "diagnosis-group", containerFactory = "diagnosisKafkaListenerContainerFactory")
    public void listen2(AddDiagnosisToMedicalHistory event) {
        medicalHistoryService.addDiagnosisEventListener(event);
    }

    @KafkaListener(topics = "prescription-events", groupId = "prescription-group", containerFactory = "prescriptionKafkaListenerContainerFactory")
    public void listen3(AddPrescriptionToMedicalHistory event) {
        medicalHistoryService.addPrescriptionEventListener(event);
    }
}

