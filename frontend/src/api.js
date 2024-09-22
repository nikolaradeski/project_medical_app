import axios from 'axios';

// For medical exam module
export const medicalExamApi = axios.create({
    baseURL: 'http://localhost:8082',
});

// For medical history module
export const medicalHistoryApi = axios.create({
    baseURL: 'http://localhost:8081',
});

// List all patients
export const getPatients = () => medicalExamApi.get('/api/patients');

//List all symptoms
export const getSymptoms = () => medicalExamApi.get('/api/patients/symptoms');

//Add symptom
// eslint-disable-next-line no-template-curly-in-string
export const addSymptom = (patientId, symptomId) => medicalExamApi.post(`/api/patients/${patientId}/addSymptom/${symptomId}`);

//Give diagnosis
export const generateDiagnosis = (patientId) => medicalExamApi.get(`/api/patients/${patientId}/diagnose`);

//Give prescription
export const generatePrescription = (patientId, prescriptionId) => medicalExamApi.get(`/api/patients/${patientId}/prescription/${prescriptionId}`);

//ConsentToTreatment
export const consentToTreatment = (patientId, prescriptionId) => medicalExamApi.get(`/api/patients/${patientId}/consentToTreatment/${prescriptionId}`);

//Get Symptom+
export const getSymptom = (symptomId) => medicalExamApi.get(`/api/patients/getSymptom/${symptomId}`);

//Get Medical History for patient
export const getMedicalHistory = (patientId) => medicalHistoryApi.get(`api/history/${patientId}`);

//Add New Patient
export const addPatient = (newPatient) => medicalExamApi.post(`api/patients/add`, newPatient);

//Edit Patient
export const editPatient = (patientId, newPatient) => medicalExamApi.post(`api/patients/edit/${patientId}`, newPatient);

//Delete Patient
export const deletePatient = (patientId) => medicalExamApi.post(`api/patients/delete/${patientId}`)