package com.example.domain.service;

import com.example.events.AddDiagnosisToMedicalHistory;
import com.example.events.AddPrescriptionToMedicalHistory;
import com.example.events.AddSymptomToMedicalHistory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final String TOPIC1 = "symptom-events";
    private static final String TOPIC2 = "diagnosis-events";
    private static final String TOPIC3 = "prescription-events";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSymptomEvent(AddSymptomToMedicalHistory event) {
        kafkaTemplate.send(TOPIC1, event);
    }
    public void sendDiagnosisEvent(AddDiagnosisToMedicalHistory event) {
        kafkaTemplate.send(TOPIC2, event);
    }
    public void sendPrescriptionEvent(AddPrescriptionToMedicalHistory event) {
        kafkaTemplate.send(TOPIC3, event);
    }
}

