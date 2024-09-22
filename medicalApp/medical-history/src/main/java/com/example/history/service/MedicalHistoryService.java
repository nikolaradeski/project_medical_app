package com.example.history.service;

import com.example.events.AddDiagnosisToMedicalHistory;
import com.example.events.AddPrescriptionToMedicalHistory;
import com.example.events.AddSymptomToMedicalHistory;
import com.example.history.model.MedicalCondition;

import java.util.List;

public interface MedicalHistoryService {
    List<MedicalCondition> listAll();
    MedicalCondition findById(Long id);
    void deleteById(Long id);
    void addSymptomEventListener(AddSymptomToMedicalHistory event);
    void addDiagnosisEventListener(AddDiagnosisToMedicalHistory event);
    void addPrescriptionEventListener(AddPrescriptionToMedicalHistory event);
    MedicalCondition getByPatientId(Long id);
    String printMedicalCondition(Long id);
}

