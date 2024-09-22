package com.example.history.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "medicalCondition")
@NoArgsConstructor
public class MedicalCondition {
    @Id
    @GeneratedValue
    Long id;
    String CurrentSymptoms;
    @Column(length = 7000)
    String PreviousDiagnoses;
    @Column(length = 7000)
    String PreviousPrescriptions;
    Long PatientId;

    public MedicalCondition(Long patientId) {
        PatientId = patientId;
        CurrentSymptoms = "";
        PreviousDiagnoses = "";
        PreviousPrescriptions = "";
    }

    public void updateSymptoms(String newSymptom) {
        if(Objects.equals(CurrentSymptoms, ""))
        {
            CurrentSymptoms += newSymptom;
        }
        else
        {
            CurrentSymptoms = CurrentSymptoms + ", " + newSymptom;
        }
    }

    public void updateDiagnosis(String newDiagnosis) {
        CurrentSymptoms = "";

        PreviousDiagnoses = PreviousDiagnoses + "-" + newDiagnosis + "\n";
    }


    public void updatePrescriptions(String newPrescription) {
        PreviousPrescriptions = PreviousPrescriptions + "-" + newPrescription + "\n";
    }

    public String getFullDescription() {
        if (Objects.isNull(CurrentSymptoms))
        {
            CurrentSymptoms = "";
        }

        if (Objects.isNull(PreviousDiagnoses))
        {
            PreviousDiagnoses = "";
        }

        if (Objects.isNull(PreviousPrescriptions))
        {
            PreviousPrescriptions = "";
        }

        if (Objects.equals(CurrentSymptoms, "") && Objects.equals(PreviousDiagnoses, "") && Objects.equals(PreviousPrescriptions, ""))
        {
            return "There is no medical history for this patient.";
        }

        StringBuilder sb = new StringBuilder();
        if (!CurrentSymptoms.equals(""))
        {
            sb.append("The patient's current symptoms are: ").append(CurrentSymptoms).append("\n");
        }
        if(!PreviousDiagnoses.equals(""))
        {
            if(!CurrentSymptoms.equals(""))
            {
                sb.append("\n");
            }
            sb.append("The patient was previously diagnosed with: \n").append(PreviousDiagnoses).append("\n");
        }
        if (!PreviousPrescriptions.equals(""))
        {
            sb.append("The patient previously agreed to the following prescriptions: \n").append(PreviousPrescriptions).append("\n");
        }

        return sb.toString();
    }
}
