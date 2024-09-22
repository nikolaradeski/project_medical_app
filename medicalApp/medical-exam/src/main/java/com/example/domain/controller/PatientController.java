package com.example.domain.controller;

import com.example.domain.DTOs.PatientDTO;
import com.example.domain.models.Diagnosis;
import com.example.domain.models.Patient;
import com.example.domain.service.PatientServiceImplementation;
import com.example.symptom.Symptom;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientServiceImplementation patientService;

    public PatientController(PatientServiceImplementation patientService) {
        this.patientService = patientService;
    }
    @GetMapping("")
    public List<Patient> getPatients()
    {
        List<Patient> allPatients = patientService.listAll();
        return allPatients;
    }

    @GetMapping("/symptoms")
    public List<Symptom> getSymptoms()
    {
        return patientService.listAllSymptoms();
    }
    @GetMapping("/getSymptom/{symptomId}")
    public Symptom getSymptom(@PathVariable Long symptomId)
    {
        return patientService.getSymptomById(symptomId);
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id)
    {
        return patientService.getPatientById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPatient(@RequestBody PatientDTO patientDTO)
    {
        if(patientDTO == null)
        {
            return ResponseEntity.notFound().build();
        }

        // Check if the patient is at least 18 years old
        if (patientDTO.getAge() < 18) {
            return ResponseEntity.badRequest().body("Patients under 18 years old cannot be added.");
        }

        patientService.create(patientDTO.getName(), patientDTO.getAge(), patientDTO.getGender(), patientDTO.getPhoneNumber(), patientDTO.getStreet(), patientDTO.getCity(), patientDTO.getPostalCode(), patientDTO.getCountry());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Void> editPatient(@PathVariable Long id, @RequestBody PatientDTO patientDTO)
    {
        if(patientDTO == null)
        {
            return ResponseEntity.notFound().build();
        }

        patientService.update(id, patientDTO.getName(), patientDTO.getAge(), patientDTO.getGender(), patientDTO.getPhoneNumber(), patientDTO.getStreet(), patientDTO.getCity(), patientDTO.getPostalCode(), patientDTO.getCountry());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id)
    {
        if(id == null)
        {
            return ResponseEntity.notFound().build();
        }

        if(patientService.findById(id) == null)
        {
            return ResponseEntity.notFound().build();
        }

        patientService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{patientId}/addSymptom/{symptomId}")
    public ResponseEntity<Void> addSymptom(@PathVariable Long patientId, @PathVariable Long symptomId) {

        if (patientId == null) {
            return ResponseEntity.notFound().build();
        }

        if (symptomId == null) {
            return ResponseEntity.notFound().build();
        }

        patientService.addSymptom(patientId, symptomId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/diagnose")
    public String diagnosePatient(@PathVariable Long id) {

        List<Diagnosis> potentialDiagnoses = patientService.giveDiagnosis(id);

        // If there are no potential diagnoses, return a message indicating that the diagnosis couldn't be determined
        if (potentialDiagnoses.isEmpty()) {
            return "Sorry, something went wrong. :(";
        }
        // If all diagnoses go through, then no Symptoms were provided
        if(potentialDiagnoses.size() == 10)
        {
            return "Sorry, we couldn't determine the diagnosis because there were no symptoms provided for this patient.";
        }

        // If there is only one potential diagnosis, return it
        if (potentialDiagnoses.size() == 1) {
            patientService.isDiagnosed(id);
            return "The patient's diagnosis might be: " + potentialDiagnoses.get(0).getDescription() + ". To see a possible prescription, click the button below.::" + potentialDiagnoses.get(0).getId();
        }

        // If there are multiple potential diagnoses, return a message that the patient should consult a doctor
        StringBuilder sb = new StringBuilder("Multiple potential diagnoses found: ");
        for (Diagnosis diagnosis : potentialDiagnoses) {
            sb.append(diagnosis.getDescription()).append(", ");
        }
        sb.append("The System was not able to give a certain diagnosis based on the provided symptoms." +
                " Please consult a doctor for further evaluation.");
        return sb.toString();
    }

    @GetMapping("/{id}/prescription/{diagnosisId}")
    public String printPrescription(@PathVariable Long id, @PathVariable Long diagnosisId)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(patientService.givePrescription(id, diagnosisId));
        Long prescriptionId = patientService.givePrescriptionId(id, diagnosisId);
        sb.append("\nIf you consent to this treatment, please click the button below.");

        return sb.toString();
    }

    @GetMapping("/{patientId}/consentToTreatment/{prescriptionId}")
    public String consentToTreatment(@PathVariable Long patientId, @PathVariable Long prescriptionId)
    {
        patientService.consentToTreatment(patientId, prescriptionId);
        return "Thank you! The prescription was added to your medical history.";
    }
}

