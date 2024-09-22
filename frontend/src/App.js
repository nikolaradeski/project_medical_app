import './App.css';
import PatientsList from "./Components/PatientsList";
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import MedicalExam from "./Components/MedicalExam";
import MedicalHistory from "./Components/MedicalHistory";
import './index.css'

function App() {
  return (
      <Router>
          <Routes>
              <Route path="/" exact element={<PatientsList/>} />
              <Route path="/medicalExam/:patientId" exact element={<MedicalExam/>} />
              <Route path="/medicalHistory/:patientId" exact element={<MedicalHistory/>} />
          </Routes>
      </Router>
  );
}

export default App;
