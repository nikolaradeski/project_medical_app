package com.example.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

public class AddSymptomToMedicalHistory{
    private Long PatientId;
    private String Symptom;

    public AddSymptomToMedicalHistory() {
    }

    @JsonCreator
    public AddSymptomToMedicalHistory(@JsonProperty("patientId") Long patientId, @JsonProperty("symptom") String symptom) {
        PatientId = patientId;
        Symptom = symptom;
    }

    public Long getPatientId() {
        return PatientId;
    }

    public String getSymptom() {
        return Symptom;
    }

    public void setPatientId(Long patientId) {
        PatientId = patientId;
    }

    public void setSymptom(String symptom) {
        Symptom = symptom;
    }
}
