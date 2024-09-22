package com.example.domain.service;

import com.example.domain.exceptions.DiagnosisNotFound;
import com.example.domain.exceptions.PatientNotFound;
import com.example.domain.exceptions.PrescriptionNotFound;
import com.example.domain.models.Patient;
import com.example.domain.repositories.DiagnosisRepository;
import com.example.domain.repositories.PatientRepository;
import com.example.domain.models.Diagnosis;
import com.example.events.AddDiagnosisToMedicalHistory;
import com.example.events.AddPrescriptionToMedicalHistory;
import com.example.events.AddSymptomToMedicalHistory;
import com.example.symptom.SymptomNotFound;
import com.example.symptom.Symptom;
import org.springframework.context.ApplicationEventPublisher;
import com.example.symptom.SymptomRepository;
import com.example.domain.models.Prescription;
import com.example.domain.repositories.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImplementation implements PatientService {
    private final PatientRepository patientRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final SymptomRepository symptomRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final KafkaProducerService kafkaProducerService;

    public PatientServiceImplementation(PatientRepository patientRepository, DiagnosisRepository diagnosisRepository, SymptomRepository symptomRepository, PrescriptionRepository prescriptionRepository, ApplicationEventPublisher eventPublisher, KafkaProducerService kafkaProducerService) {
        this.patientRepository = patientRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.symptomRepository = symptomRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.kafkaProducerService = kafkaProducerService;
//        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<Patient> listAll() {
        return patientRepository.findAll();
    }

    @Override
    public Patient findById(Long id) {
        return patientRepository.findById(id).orElseThrow(PatientNotFound::new);
    }

    @Override
    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }

    @Override
    public void addSymptom(Long patientId, Long symptomId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(PatientNotFound::new);
        Symptom symptom = symptomRepository.findById(symptomId).orElseThrow(SymptomNotFound::new);
        patient.addSymptom(symptom);
        AddSymptomToMedicalHistory event = new AddSymptomToMedicalHistory(patientId, symptom.getName());
        kafkaProducerService.sendSymptomEvent(event);
        patientRepository.save(patient);
    }

    @Override
    public Patient create(String name, Integer age, String gender, String phoneNumber, String street, String city, Integer postalCode, String country) {
        Patient patient = new Patient(name, age, gender, phoneNumber, street, city, postalCode, country);
        return patientRepository.save(patient);
    }

    @Override
    public Patient update(Long id, String name, Integer age, String gender, String phoneNumber, String street, String city, Integer postalCode, String country) {
        Patient patient = patientRepository.findById(id).orElseThrow(PatientNotFound::new);
        patient.setName(name);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setPhoneNumber(phoneNumber);
        patient.setStreet(street);
        patient.setCity(city);
        patient.setPostalCode(postalCode);
        patient.setCountry(country);
        patient.setStringAddress(street, city, postalCode, country);
        return patientRepository.save(patient);
    }

    @Override
    public List<Diagnosis> giveDiagnosis(Long patientId) {
        // se vrakja lista so edna ili povekje dijagnozi koi sodrzhat najmnogu simptomi
        // od onie koi gi ima pacientot.
        Patient patient = patientRepository.findById(patientId).orElseThrow(PatientNotFound::new);
        List<Symptom> patientSymptoms = patient.getSymptoms();

        int maxSymptomsCount = 0;
        int currentSymptomCount = 0;
        List<Diagnosis> diagnoses = diagnosisRepository.findAll();
        List<Diagnosis> potentialDiagnoses = diagnosisRepository.findAll();;

        for (Diagnosis diagnosis : diagnoses)
        {
            for (Symptom symptom : patientSymptoms)
            {
                // Symptoms: Fever, cough, headache, fatigue, muscle aches, sore throat.
                if (diagnosis.getDescription().equals("Influenza (Flu)") && (symptom.getName().equals("Fever") || symptom.getName().equals("Cough") ||
                        symptom.getName().equals("Headache") || symptom.getName().equals("Fatigue") || symptom.getName().equals("Sore throat") || symptom.getName().equals("Muscle aches")))
                {
                    currentSymptomCount++;
                }

                // Symptoms: Runny nose, congestion, cough, sore throat, fatigue.
                else if (diagnosis.getDescription().equals("Common Cold") && (symptom.getName().equals("Congestion or runny nose") || symptom.getName().equals("Cough") ||
                        symptom.getName().equals("Fatigue") || symptom.getName().equals("Sore throat")))
                {
                    currentSymptomCount++;
                }

                // Symptoms: Fever, cough, shortness of breath, loss of taste or smell, fatigue, headache.
                else if (diagnosis.getDescription().equals("COVID-19 (Coronavirus)") && (symptom.getName().equals("Fever") || symptom.getName().equals("Cough") ||
                        symptom.getName().equals("Shortness of breath") || symptom.getName().equals("Loss of taste or smell") || symptom.getName().equals("Headache")))
                {
                    currentSymptomCount++;
                }

                // Symptoms: Runny nose, congestion, fever, or cough.
                else if (diagnosis.getDescription().equals("Seasonal Allergies") && (symptom.getName().equals("Congestion or runny nose") || symptom.getName().equals("Cough") ||
                        symptom.getName().equals("Fever")))
                {
                    currentSymptomCount++;
                }

                // Symptoms: Severe headache, nausea, sensitivity to light and sound.
                else if (diagnosis.getDescription().equals("Migraine") && (symptom.getName().equals("Headache") || symptom.getName().equals("Nausea or vomiting") ||
                        symptom.getName().equals("Sensitivity to light and sound")))
                {
                    currentSymptomCount++;
                }

                // Symptoms: Sore throat, fever, difficulty swallowing, swollen tonsils.
                else if (diagnosis.getDescription().equals("Strep Throat") && (symptom.getName().equals("Sore throat") || symptom.getName().equals("Fever") ||
                        symptom.getName().equals("Difficulty swallowing") || symptom.getName().equals("Swollen tonsils")))
                {
                    currentSymptomCount++;
                }

                // Symptoms: Fever, cough, shortness of breath, chest pain, fatigue.
                else if (diagnosis.getDescription().equals("Pneumonia") && (symptom.getName().equals("Cough") || symptom.getName().equals("Fever") ||
                        symptom.getName().equals("Shortness of breath") || symptom.getName().equals("Chest pain") || symptom.getName().equals("Fatigue")))
                {
                    currentSymptomCount++;
                }

                // Symptoms: Nausea, vomiting, diarrhea, abdominal pain, fever.
                else if (diagnosis.getDescription().equals("Gastroenteritis (Stomach Flu)") && (symptom.getName().equals("Nausea or vomiting") || symptom.getName().equals("Fever") ||
                        symptom.getName().equals("Diarrhea") || symptom.getName().equals("Abdominal pain")))
                {
                    currentSymptomCount++;
                }

                // Symptoms: Shortness of breath, wheezing, chest pain, coughing.
                else if (diagnosis.getDescription().equals("Asthma") && (symptom.getName().equals("Shortness of breath") || symptom.getName().equals("Wheezing") ||
                        symptom.getName().equals("Chest pain") || symptom.getName().equals("Coughing")))
                {
                    currentSymptomCount++;
                }

                // Symptoms: Facial pain or pressure, congestion, runny nose, headache.
                else if (diagnosis.getDescription().equals("Sinusitis") && (symptom.getName().equals("Facial pain or pressure") || symptom.getName().equals("Congestion or runny nose") ||
                        symptom.getName().equals("Headache")))
                {
                    currentSymptomCount++;
                }
            }

            if(currentSymptomCount > maxSymptomsCount)
            {
                potentialDiagnoses.clear();
                potentialDiagnoses.add(diagnosis);
                maxSymptomsCount = currentSymptomCount;
            }
            else if(currentSymptomCount == maxSymptomsCount)
            {
                potentialDiagnoses.add(diagnosis);
            }

            currentSymptomCount = 0;
        }

        if (potentialDiagnoses.size() == 1)
        {
            AddDiagnosisToMedicalHistory event = new AddDiagnosisToMedicalHistory(patientId, potentialDiagnoses.get(0).getDescription());
            kafkaProducerService.sendDiagnosisEvent(event);
        }

        return potentialDiagnoses;
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(PatientNotFound::new);
    }

    @Override
    public void isDiagnosed(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(PatientNotFound::new);
        patient.setDiagnosed(true);
        patient.setSymptoms(null);
        patientRepository.save(patient);
    }

    @Override
    public String givePrescription(Long id, Long diagnosisId) {
        Patient patient = patientRepository.findById(id).orElseThrow(PatientNotFound::new);
        if(!patient.getIsDiagnosed())
        {
            return "The patient is not diagnosed with a diagnosis.";
        }
        else
        {
            Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId).orElseThrow(DiagnosisNotFound::new);
            for(Prescription p : prescriptionRepository.findAll())
            {
                if(p.getDiagnosis().equals(diagnosis))
                {
                    return p.getDescription();
                }
            }
        }
        return null;
    }



    @Override
    public Long givePrescriptionId(Long id, Long diagnosisId) {
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId).orElseThrow(DiagnosisNotFound::new);
        for(Prescription p : prescriptionRepository.findAll())
        {
            if(p.getDiagnosis().equals(diagnosis))
            {
                return p.getId();
            }
        }
        return null;
    }
    @Override
    public void consentToTreatment(Long patientId, Long prescriptionId)
    {
        Prescription p = prescriptionRepository.findById(prescriptionId).orElseThrow(PrescriptionNotFound::new);
        AddPrescriptionToMedicalHistory event = new AddPrescriptionToMedicalHistory(patientId, p.getDescription());
        kafkaProducerService.sendPrescriptionEvent(event);
    }

    @Override
    public List<Symptom> listAllSymptoms() {
        return symptomRepository.findAll();
    }

    @Override
    public Symptom getSymptomById(Long symptomId) {
        return symptomRepository.findById(symptomId).orElseThrow(SymptomNotFound::new);
    }

}

