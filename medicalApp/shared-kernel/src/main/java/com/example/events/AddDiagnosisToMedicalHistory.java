package com.example.events;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddDiagnosisToMedicalHistory {
    private Long PatientId;
    private String Diagnosis;

    @JsonCreator
    public AddDiagnosisToMedicalHistory(@JsonProperty("patientId") Long patientId, @JsonProperty("diagnosis") String diagnosis) {
        PatientId = patientId;
        Diagnosis = diagnosis;
    }

    public Long getPatientId() {
        return PatientId;
    }

    public String getDiagnosis() {
        return Diagnosis;
    }
}
