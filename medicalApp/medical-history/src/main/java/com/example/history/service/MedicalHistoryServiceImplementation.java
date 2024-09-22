package com.example.history.service;

import com.example.events.AddDiagnosisToMedicalHistory;
import com.example.events.AddPrescriptionToMedicalHistory;
import com.example.events.AddSymptomToMedicalHistory;
import com.example.history.model.MedicalCondition;
import com.example.history.exception.MedicalConditionNotFound;
import com.example.history.repository.MedicalConditionRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
@Service
public class MedicalHistoryServiceImplementation implements MedicalHistoryService{
    private final MedicalConditionRepository medicalConditionRepository;

    public MedicalHistoryServiceImplementation(MedicalConditionRepository medicalConditionRepository) {
        this.medicalConditionRepository = medicalConditionRepository;
    }

    @Override
    public List<MedicalCondition> listAll() {
        return medicalConditionRepository.findAll();
    }

    @Override
    public MedicalCondition findById(Long id) {
        return medicalConditionRepository.findById(id).orElseThrow(MedicalConditionNotFound::new);
    }

    @Override
    public void deleteById(Long id) {
        medicalConditionRepository.deleteById(id);
    }

    @Override
    public void addSymptomEventListener(AddSymptomToMedicalHistory event) {
        Long patientId = event.getPatientId();
        String newSymptom = event.getSymptom();

        MedicalCondition newMedicalCondition = getByPatientId(patientId);

        if (newMedicalCondition == null)
        {
            MedicalCondition medicalCondition = new MedicalCondition(patientId);
            medicalCondition.updateSymptoms(newSymptom);
            medicalConditionRepository.save(medicalCondition);
        }
        else
        {
            newMedicalCondition.updateSymptoms(newSymptom);
            medicalConditionRepository.save(newMedicalCondition);
        }
    }

    @Override
    public void addDiagnosisEventListener(AddDiagnosisToMedicalHistory event) {
        Long patientId = event.getPatientId();
        String newDiagnosis = event.getDiagnosis();
        MedicalCondition newMedicalCondition = getByPatientId(patientId);

        if (newMedicalCondition == null)
        {
            MedicalCondition medicalCondition = new MedicalCondition(patientId);
            medicalCondition.updateDiagnosis(newDiagnosis);
            medicalConditionRepository.save(medicalCondition);
        }
        else
        {
            newMedicalCondition.updateDiagnosis(newDiagnosis);
            medicalConditionRepository.save(newMedicalCondition);
        }
    }

    @Override
    public void addPrescriptionEventListener(AddPrescriptionToMedicalHistory event) {
        Long patientId = event.getPatientId();
        String newPrescription = event.getPrescription();
        MedicalCondition newMedicalCondition = getByPatientId(patientId);

        if (newMedicalCondition == null)
        {
            MedicalCondition medicalCondition = new MedicalCondition(patientId);
            medicalCondition.updatePrescriptions(newPrescription);
            medicalConditionRepository.save(medicalCondition);
        }
        else
        {
            newMedicalCondition.updatePrescriptions(newPrescription);
            medicalConditionRepository.save(newMedicalCondition);
        }
    }

    @Override
    public MedicalCondition getByPatientId(Long id) {
        List<MedicalCondition> medicalConditions = medicalConditionRepository.findAll();

        for (MedicalCondition mc : medicalConditions)
        {
            if (Objects.equals(mc.getPatientId(), id))
            {
                return mc;
            }
        }
        return null;
    }

    @Override
    public String printMedicalCondition(Long id) {
        MedicalCondition mc = getByPatientId(id);

        if(mc == null)
        {
            MedicalCondition newMedicalCondition = new MedicalCondition(id);
            return newMedicalCondition.getFullDescription();
        }

        return mc.getFullDescription();
    }
}
