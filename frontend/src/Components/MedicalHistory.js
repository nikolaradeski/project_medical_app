import React, { useEffect, useState } from 'react';
import {getMedicalHistory, getPatients} from '../api';
import {useNavigate, useParams} from "react-router-dom";

const MedicalHistory = () => {
    const { patientId } = useParams();
    const [description, setDescription] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        getMedicalHistory(patientId)
            .then(response => {
                setDescription(response.data)
                console.log(response.data)
            });
    }, []);

    function handleBackButton() {
        navigate(`/`);
    }

    return (
        <div>
            <h2>Medical History for Patient with ID: {patientId}</h2>

            <div><p className="medicalHistoryText" style={{ whiteSpace: 'pre-line' }}>{description}</p></div>

            <button className="back-button" onClick={() => handleBackButton()}>
                Back
            </button>
        </div>
    );
};


export default MedicalHistory;
