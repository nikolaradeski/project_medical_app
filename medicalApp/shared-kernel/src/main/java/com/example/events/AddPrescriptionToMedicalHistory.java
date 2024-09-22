package com.example.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddPrescriptionToMedicalHistory {
    private Long PatientId;
    private String Prescription;

    @JsonCreator
    public AddPrescriptionToMedicalHistory(@JsonProperty("patientId") Long patientId, @JsonProperty("prescription") String prescription) {
        PatientId = patientId;
        Prescription = prescription;
    }

    public Long getPatientId() {
        return PatientId;
    }

    public String getPrescription() {
        return Prescription;
    }
}
