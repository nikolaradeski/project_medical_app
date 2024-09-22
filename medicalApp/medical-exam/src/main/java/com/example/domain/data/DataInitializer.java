package com.example.domain.data;

import com.example.domain.models.Patient;
import com.example.domain.models.Prescription;
import com.example.domain.repositories.PrescriptionRepository;
import com.example.symptom.Symptom;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import com.example.domain.models.Diagnosis;
import org.springframework.stereotype.Component;
import com.example.domain.repositories.DiagnosisRepository;
import com.example.domain.repositories.PatientRepository;
import com.example.symptom.SymptomRepository;
import java.util.Arrays;


@Component
@AllArgsConstructor
public class DataInitializer {

    private final DiagnosisRepository diagnosisRepository;
    private final PatientRepository patientRepository;
    private final SymptomRepository symptomRepository;
    private final PrescriptionRepository prescriptionRepository;
    @PostConstruct
    public void initData() {
        Patient p1 = new Patient("Bobi", 23, "male", "077111222","Goce Delcev","Skopje",1000,"Macedonia");
        Patient p2 = new Patient("Michael", 30, "male", "077111222", "Bulevar Slovenija", "Skopje", 1000, "Macedonia");
        Patient p3 = new Patient("Aleksandar", 20, "male", "077111222", "John Kennedy", "Tetovo", 1004, "Macedonia");
        Patient p4 = new Patient("Jovan", 52, "male", "077111222", "Knez Mihajlova", "Belgrade", 5000, "Serbia");
        Patient p5 = new Patient("Jack", 43, "male", "077111222", "5th Avenue", "New York" ,24239, "USA");

        if (patientRepository.findAll().isEmpty()) {
            patientRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5));
        }

        Symptom s1 = new Symptom("Fever");
        Symptom s2 = new Symptom("Cough");
        Symptom s3 = new Symptom("Headache");
        Symptom s4 = new Symptom("Fatigue");
        Symptom s5 = new Symptom("Sore throat");
        Symptom s6 = new Symptom("Shortness of breath");
        Symptom s7 = new Symptom("Muscle aches");
        Symptom s8 = new Symptom("Loss of taste or smell");
        Symptom s9 = new Symptom("Congestion or runny nose");
        Symptom s10 = new Symptom("Nausea or vomiting");
        Symptom s11 = new Symptom("Sensitivity to light and sound");
        Symptom s12 = new Symptom("Difficulty swallowing");
        Symptom s13 = new Symptom("Swollen tonsils");
        Symptom s14 = new Symptom("Chest pain");
        Symptom s15 = new Symptom("Diarrhea");
        Symptom s16 = new Symptom("Abdominal pain");
        Symptom s17 = new Symptom("Wheezing");
        Symptom s18 = new Symptom("Facial pain or pressure");

        if (symptomRepository.findAll().isEmpty()) {
            symptomRepository.saveAll(Arrays.asList(s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13, s14, s15, s16, s17, s18));
        }

        // Symptoms: Fever, cough, headache, fatigue, muscle aches, sore throat.
        Diagnosis d1 = new Diagnosis("Influenza (Flu)");

        // Symptoms: Runny nose, congestion, cough, sore throat, fatigue.
        Diagnosis d2 = new Diagnosis("Common Cold");

        // Symptoms: Fever, cough, shortness of breath, loss of taste or smell, fatigue, headache.
        Diagnosis d3 = new Diagnosis("COVID-19 (Coronavirus)");

        // Symptoms: Runny nose, congestion, sneezing, itchy or cough.
        Diagnosis d4 = new Diagnosis("Seasonal Allergies");

        // Symptoms: Severe headache, nausea, sensitivity to light and sound.
        Diagnosis d5 = new Diagnosis("Migraine");

        // Symptoms: Sore throat, fever, difficulty swallowing, swollen tonsils.
        Diagnosis d6 = new Diagnosis("Strep Throat");

        // Symptoms: Fever, cough, shortness of breath, chest pain, fatigue.
        Diagnosis d7 = new Diagnosis("Pneumonia");

        // Symptoms: Nausea, vomiting, diarrhea, abdominal pain, fever.
        Diagnosis d8 = new Diagnosis("Gastroenteritis (Stomach Flu)");

        // Symptoms: Shortness of breath, wheezing, chest pain, coughing.
        Diagnosis d9 = new Diagnosis("Asthma");

        // Symptoms: Facial pain or pressure, congestion, runny nose, headache.
        Diagnosis d10 = new Diagnosis("Sinusitis");

        if (diagnosisRepository.findAll().isEmpty()) {
            diagnosisRepository.saveAll(Arrays.asList(d1,d2,d3,d4,d5, d6, d7, d8, d9, d10));
        }

        Prescription pr1 = new Prescription("Take ibuprofen every 4-6 hours as needed for fever and body aches. Get plenty of rest and drink fluids.", null, d1);
        Prescription pr2 = new Prescription("Take over-the-counter cold medication containing antihistamines and decongestants as directed. Rest and drink plenty of fluids.", null, d2);
        Prescription pr3 = new Prescription("Take invermectine, vitamine D, magnesium and vitamine C. Isolate yourself and contact a healthcare provider for COVID-19 testing. Follow all guidelines for self-quarantine and monitor symptoms closely." , null, d3);
        Prescription pr4 = new Prescription("Use over-the-counter allergy medication such as loratadine or cetirizine as directed. Avoid allergens and use saline nasal spray for congestion.", null, d4);
        Prescription pr5 = new Prescription("Take acetaminophen or ibuprofen for headache and nausea as needed. Lie down in a dark, quiet room and apply a cold compress to the forehead.", null, d5);
        Prescription pr6 = new Prescription("Start antibiotics as prescribed by your healthcare provider. Take all medication as directed and rest at home until symptoms improve.", null, d6);
        Prescription pr7 = new Prescription("Stay hydrated and rest at home. Eat bland foods and avoid dairy, caffeine, and alcohol. Contact a healthcare provider if symptoms worsen.", null, d7);
        Prescription pr8 = new Prescription("Use a rescue inhaler for asthma attacks and continue maintenance inhalers as prescribed. Avoid triggers such as smoke and allergens.", null, d8);
        Prescription pr9 = new Prescription("Take over-the-counter pain relievers for sinus pain and pressure. Use saline nasal spray and steam inhalation for congestion.", null, d9);
        Prescription pr10 = new Prescription("Take antibiotics as prescribed for strep throat. Gargle with warm salt water and use throat lozenges for throat pain.", null, d10);

        if (prescriptionRepository.findAll().isEmpty()) {
            prescriptionRepository.saveAll(Arrays.asList(pr1,pr2,pr3,pr4,pr5, pr6, pr7, pr8, pr9, pr10));
        }
    }
}

