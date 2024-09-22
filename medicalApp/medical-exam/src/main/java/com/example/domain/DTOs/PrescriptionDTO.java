package com.example.domain.DTOs;

import com.example.domain.models.Diagnosis;
import com.example.domain.models.Patient;
import lombok.Data;

@Data
public class PrescriptionDTO {
    String description;
    Patient patient;
    Diagnosis diagnosis;
}
