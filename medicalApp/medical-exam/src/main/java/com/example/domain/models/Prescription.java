package com.example.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "prescriptions")
@NoArgsConstructor
public class Prescription{
    @Id
    @GeneratedValue
    Long id;
    String description;
    @ManyToOne
    Patient patient;
    @OneToOne
    Diagnosis diagnosis;

    public Prescription(String description, Patient patient, Diagnosis diagnosis) {
        this.description = description;
        this.patient = patient;
        this.diagnosis = diagnosis;
    }

    public String getDescription() {
        return description;
    }

    public Patient getPatient() {
        return patient;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }
}
