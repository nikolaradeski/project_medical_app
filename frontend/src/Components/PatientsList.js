import React, { useEffect, useState } from 'react';
import {addPatient, editPatient, getPatients, deletePatient} from '../api';
import { useNavigate } from "react-router-dom";


const PatientsList = () => {
    const [patients, setPatients] = useState([]);
    const [loading, setLoading] = useState(true);
    const [showForm, setShowForm] = useState(false);
    const [editForm, setEditForm] = useState(false);
    const [editPatientId, setEditPatientId] = useState(0);
    const [newPatient, setNewPatient] = useState({
        name: '',
        age: 0,
        gender: '',
        phoneNumber: '',
        street: "",
        city: "",
        postalCode: 0,
        country: "",
    });
    const [editedPatient, setEditedPatient] = useState({
        name: '',
        age: 0,
        gender: '',
        phoneNumber: '',
        street: "",
        city: "",
        postalCode: 0,
        country: ""
    });
    const navigate = useNavigate();

    useEffect(() => {
        getPatients()
            .then(response => {
                setPatients(response.data);
                console.log(response.data)
                setLoading(false);
            })
            .catch(error => {
                console.error('Error fetching patients:', error);
                setLoading(false);
            });
    }, []);


    if (loading) {
        return <div>Loading...</div>;
    }
    // eslint-disable-next-line react-hooks/rules-of-hooks

    const handleExam = (patientId) => {
        navigate(`/medicalExam/${patientId}`);
    };

    function handleHistory(patientId) {
        navigate(`/medicalHistory/${patientId}`);
    }

    function handleAddNewPatientButton() {
        setShowForm(true)
        setEditForm(false)
    }

    const handleAddNewPatient = async () => {
        try {
            await addPatient(newPatient);
            setPatients(prevPatients => [...prevPatients, newPatient]);
            setNewPatient({ name: '', age: 0, gender: '', phoneNumber: '', street: "", city: "", postalCode: 0, country: "",});
            setShowForm(false);
            const response = await getPatients()
            setPatients(response.data);
        } catch (error) {
            console.error('Error adding patient:', error);
        }
    };

    const handleInputChangeAdd = (e) => {
        const { name, value } = e.target;
        setNewPatient(prevState => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleInputChangeEdit = (e) => {
        const { name, value } = e.target;
        setEditedPatient(prevState => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleEditPatientButton = async (patientId) => {
        setShowForm(false)
        setEditForm(true)

        const patient = patients.find(p => p.id === parseInt(patientId));
        setEditPatientId(patientId)
        setEditedPatient(prevState => ({
            ...prevState,
            name: patient.name,
            age: patient.age,
            gender: patient.gender,
            phoneNumber: patient.phoneNumber,
            street: patient.stringAddress.split(", ")[0],
            city: patient.stringAddress.split(", ")[1],
            postalCode: patient.stringAddress.split(", ")[2],
            country: patient.stringAddress.split(", ")[3],
        }));

    };

    const handleEditPatient = async () => {
        try {
            await editPatient(editPatientId, editedPatient);
            setEditedPatient({ name: '', age: 0, gender: '', phoneNumber: '', street: "", city: "", postalCode: 0, country: ""});
            const response = await getPatients()
            setPatients(response.data);
            setEditForm(false);
        } catch (error) {
            console.error('Error editing patient:', error);
        }
    };

    const handleDeletePatientButton = async (patientId) => {
        await deletePatient(patientId)
        const response = await getPatients()
        setPatients(response.data);
    }

    function handleBackButton() {
        setNewPatient({ name: '', age: 0, gender: '', phoneNumber: '', street: "", city: "", postalCode: 0, country: "", address: ""});
        setEditedPatient({ name: '', age: 0, gender: '', phoneNumber: '', street: "", city: "", postalCode: 0, country: "" });
        setEditPatientId(0)
        setEditForm(false)
        setShowForm(false)
    }

    return (
        <div>
            {/* eslint-disable-next-line react/style-prop-object */}
            <h1>Patients List</h1>
            <table style={{
                margin: '0 auto',
                border: '1px solid black',
                borderCollapse: 'collapse'
            }}
                   border="1"
                   cellPadding="10"
                   cellSpacing="0">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Age</th>
                    <th>Gender</th>
                    <th>Phone Number</th>
                    <th>Address</th>
                    <th>Edit Patient</th>
                    <th>Delete Patient</th>
                    <th>Exam Patient</th>
                    <th>Medical History</th>
                </tr>
                </thead>
                <tbody>
                {patients.length > 0 ? (
                    patients.map((patient) => (
                        <tr key={patient.id}>
                            <td>{patient.name}</td>
                            <td>{patient.age}</td>
                            <td>{patient.gender}</td>
                            <td>{patient.phoneNumber}</td>
                            <td>{patient.stringAddress}</td>
                            <td>
                                <button className="editPatientButton" onClick={() => handleEditPatientButton(patient.id)}>
                                    Edit
                                </button>
                            </td>
                            <td>
                                <button className="examPatientButton deletePatientButton" onClick={() => handleDeletePatientButton(patient.id)}>
                                    Delete
                                </button>
                            </td>
                            <td>
                                <button className="examPatientButton" onClick={() => handleExam(patient.id)}>
                                    Exam
                                </button>
                            </td>
                            <td>
                                <button className="historyPatientButton" onClick={() => handleHistory(patient.id)}>
                                    History
                                </button>
                            </td>
                        </tr>
                    ))
                ) : (
                    <tr>
                        <td colSpan="5">No patients found</td>
                    </tr>
                )}
                </tbody>
            </table>
            <br />
            <button className="action-button" onClick={handleAddNewPatientButton}>Add New Patient</button>
            <br/>
            <hr/>
            <br/>
{/*----------------------------ADD NEW PATIENT FORM--------------------------*/}
            {showForm && (
                <form className="form-container" onSubmit={(e) => {
                    e.preventDefault();
                    handleAddNewPatient();
                }}>
                    <h3>New Patient Details:</h3>
                    <label>
                        Name:
                        <input
                            type="text"
                            name="name"
                            value={newPatient.name}
                            onChange={handleInputChangeAdd}
                            required
                        />
                    </label>
                    <label>
                        Age:
                        <input
                            type="number"
                            name="age"
                            value={parseInt(newPatient.age)}
                            onChange={handleInputChangeAdd}
                            required
                        />
                    </label>
                    <label>
                        Gender:
                        <select
                            name="gender"
                            value={newPatient.gender}
                            onChange={handleInputChangeAdd}
                            required
                        >
                            <option value="">Select Gender</option>
                            <option value="male">male</option>
                            <option value="female">female</option>
                        </select>
                    </label>
                    <label>
                        Phone Number:
                        <input
                            type="text"
                            name="phoneNumber"
                            value={newPatient.phoneNumber}
                            onChange={handleInputChangeAdd}
                            required
                        />
                    </label>
                    <label>
                        Street Name:
                        <input
                            type="text"
                            name="street"
                            value={newPatient.street}
                            onChange={handleInputChangeAdd}
                            required
                        />
                    </label>
                    <label>
                        City:
                        <input
                            type="text"
                            name="city"
                            value={newPatient.city}
                            onChange={handleInputChangeAdd}
                            required
                        />
                    </label>
                    <label>
                        Postal Code:
                        <input
                            type="text"
                            name="postalCode"
                            value={newPatient.postalCode}
                            onChange={handleInputChangeAdd}
                            required
                        />
                    </label>
                    <label>
                        Country:
                        <input
                            type="text"
                            name="country"
                            value={newPatient.country}
                            onChange={handleInputChangeAdd}
                            required
                        />
                    </label>
                    <button className="submit-button" type="submit">Save</button>
                    <button className="back-button" onClick={handleBackButton}>Back</button>
                </form>
            )}

{/*----------------------------EDIT PATIENT FORM--------------------------*/}
            {editForm && (
                <form className="form-container" onSubmit={(e) => {
                    e.preventDefault();
                    handleEditPatient();
                }}>
                    <h3>Patient Details:</h3>
                    <label>
                        Name:
                        <input
                            type="text"
                            name="name"
                            value={editedPatient.name}
                            onChange={handleInputChangeEdit}
                            required
                        />
                    </label>
                    <label>
                        Age:
                        <input
                            type="number"
                            name="age"
                            value={parseInt(editedPatient.age)}
                            onChange={handleInputChangeEdit}
                            required
                        />
                    </label>
                    <label>
                        Gender:
                        <select
                            name="gender"
                            value={editedPatient.gender}
                            onChange={handleInputChangeEdit}
                            required
                        >
                            <option value="">Select Gender</option>
                            <option value="male">male</option>
                            <option value="female">female</option>
                        </select>
                    </label>
                    <label>
                        Phone Number:
                        <input
                            type="text"
                            name="phoneNumber"
                            value={editedPatient.phoneNumber}
                            onChange={handleInputChangeEdit}
                            required
                        />
                    </label>
                    <label>
                        Street Name:
                        <input
                            type="text"
                            name="street"
                            value={editedPatient.street}
                            onChange={handleInputChangeEdit}
                            required
                        />
                    </label>
                    <label>
                        City:
                        <input
                            type="text"
                            name="city"
                            value={editedPatient.city}
                            onChange={handleInputChangeEdit}
                            required
                        />
                    </label>
                    <label>
                        Postal Code:
                        <input
                            type="text"
                            name="postalCode"
                            value={editedPatient.postalCode}
                            onChange={handleInputChangeEdit}
                            required
                        />
                    </label>
                    <label>
                        Country:
                        <input
                            type="text"
                            name="country"
                            value={editedPatient.country}
                            onChange={handleInputChangeEdit}
                            required
                        />
                    </label>
                    <button className="submit-button" type="submit">Save</button>
                    <button className="back-button" onClick={handleBackButton}>Back</button>
                </form>
            )}
        </div>
    );
};

export default PatientsList;
