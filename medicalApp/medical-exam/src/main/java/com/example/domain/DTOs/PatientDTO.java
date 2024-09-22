package com.example.domain.DTOs;

import com.example.symptom.Symptom;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PatientDTO {
    String name;
    Integer age;
    String gender;
    String phoneNumber;
    List<Symptom> symptoms = new ArrayList<Symptom>();
    Boolean isDiagnosed = false;
    String street;
    String city;
    Integer postalCode;
    String country;

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }
}
