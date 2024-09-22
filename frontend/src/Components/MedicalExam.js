import React, { useEffect, useState } from 'react';
import {getSymptoms, addSymptom, generateDiagnosis, generatePrescription, consentToTreatment, getSymptom} from "../api";
import {useParams, useNavigate} from "react-router-dom";



const MedicalExam = () => {
    const { patientId } = useParams();
    const [selectedSymptom, setSelectedSymptom] = useState("");
    const [symptoms, setSymptoms] = useState([]);
    const [selectedSymptoms, setSelectedSymptoms] = useState([]);
    const [diagnosis, setDiagnosis] = useState([]);
    const [prescription, setPrescription] = useState([]);
    const [prescriptionId, setPrescriptionId] = useState([]);
    const [finalMessage, setFinalMessage] = useState([]);
    const [hasAddedSymptom, setHasAddedSymptom] = useState(false);
    const [isDiagnosed, setIsDiagnosed] = useState(false);
    const [hasMoreDiagnoses, sethasMoreDiagnoses] = useState(false);
    const [hasSeenPrescription, setHasSeenPrescription] = useState(false);
    const [hasAgreedToPrescription, setHasAgreedToPrescription] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        getSymptoms()
            .then(response => {
                setSymptoms(response.data); // Store symptoms in state
                setHasAgreedToPrescription(false);
            })
            .catch(error => {
                console.error('Error fetching symptoms:', error);
            });
    }, []);

    const handleAddSymptom = async (symptomId) => {
        try {
            const symptom = symptoms.find(s => s.id === parseInt(symptomId));
            if(symptom)
            {
                await addSymptom(patientId, symptomId);
                setSelectedSymptoms((prev) => [...prev, symptom]); // Add full symptom object to selected symptoms
                setHasAddedSymptom(true); // Enable diagnosis button
            }
            else
            {
                console.log(symptomId)
            }
        } catch (error) {
            console.error("Error adding symptom:", error);
        }
    };

    const handleGenerateDiagnosis = async () => {
        try {
            const response = await generateDiagnosis(patientId);
            setPrescriptionId(response.data.split("::")[1])
            setDiagnosis(response.data.split("::")[0]);
            if(response.data[0] !== 'M')
            {
                sethasMoreDiagnoses(true); // Enable prescription button
            }
            setIsDiagnosed(true);

        } catch (error) {
            console.error("Error generating diagnosis:", error);
        }
    };

    const handleGeneratePrescription = async () => {
        try {
            const response = await generatePrescription(patientId, prescriptionId);
            setPrescription(response.data);
            setHasSeenPrescription(true); // Enable consent button
        } catch (error) {
            console.error("Error generating prescription:", error);
        }
    };

    const handleSaveTreatment = async () => {
        try {
            const response = await consentToTreatment(patientId, parseInt(prescriptionId));
            console.log(patientId + "  " + prescriptionId)
            setFinalMessage(response.data);
            setHasAgreedToPrescription(true);
        } catch (error) {
            console.error("Error saving treatment:", error);
        }
    };

    function handleBackButton() {
        navigate(`/`);
    }

    return (
        <div>
            <h2>Medical Exam for Patient with ID: {patientId}</h2>

            {/*---------------------------ADD SYMPTOM------------------------------------------- */}
            <div>
                <h3>Add Symptom for Patient {patientId}</h3>
                <select value={selectedSymptom} onChange={(e) => setSelectedSymptom(e.target.value)}>
                    <option value="">Select a symptom</option>
                    {symptoms.map((symptom) => (
                        <option key={symptom.id} value={symptom.id}>
                            {symptom.name}
                        </option>
                    ))}
                </select>
                <button className="action-button" onClick={() => handleAddSymptom(selectedSymptom)} disabled={!selectedSymptom}>
                    Add Symptom
                </button>
            </div>

            {/*---------------------------DISPLAY SELECTED SYMPTOMS-------------------------------------------*/}
            <h3>Selected Symptoms:</h3>
            <ul>
                {selectedSymptoms.map(symptom => (
                    <li key={symptom.id}>{symptom.name}</li>
                ))}
            </ul>
            <hr/>
            {/*---------------------------GENERATE DIAGNOSIS------------------------------------------- */}
            {hasAddedSymptom  && <button className="action-button" onClick={handleGenerateDiagnosis}>
                Generate Diagnosis
            </button>}

            {isDiagnosed && <div><h3>Diagnosis:</h3><p>{diagnosis}</p><hr/></div>}


            {/*---------------------------GENERATE PRESCRIPTION------------------------------------------- */}
            {isDiagnosed && hasMoreDiagnoses && <button className="action-button" onClick={handleGeneratePrescription}>
                Generate Prescription
            </button>}

            {hasSeenPrescription && <div><h3>Prescription:</h3><p>{prescription}</p><hr/></div>}




            {/*---------------------------CONSENT TO TREATMENT----------------------------------------------*/}
            {hasSeenPrescription && <button className="action-button" onClick={handleSaveTreatment}>
                Patient's Consent
            </button>}

            {hasAgreedToPrescription && <div><p>{finalMessage}</p></div>}

            <button className="back-button" onClick={() => handleBackButton()}>
                Back
            </button>
        </div>
    );
};

export default MedicalExam;