package com.example.domain.service;

import com.example.domain.models.Patient;
import com.example.domain.models.Diagnosis;
import com.example.symptom.Symptom;

import java.util.List;

public interface PatientService {
    List<Patient> listAll();
    Patient findById(Long id);
    void deleteById(Long id);
    void addSymptom(Long patientId, Long symptomId);
    Patient create(String name, Integer age, String gender, String phoneNumber, String streetName, String city, Integer postalCode, String country);
    Patient update(Long id, String name, Integer age, String gender, String phoneNumber, String street, String city, Integer postalCode, String country);
    List<Diagnosis> giveDiagnosis(Long patientId);
    Patient getPatientById(Long id);

    void isDiagnosed(Long id);

    String givePrescription(Long id, Long diagnosisId);

    Long givePrescriptionId(Long id, Long diagnosisId);
    void consentToTreatment(Long patientId, Long prescriptionId);

    List<Symptom> listAllSymptoms();

    Symptom getSymptomById(Long symptomId);
}
