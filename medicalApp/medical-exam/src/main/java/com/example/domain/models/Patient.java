package com.example.domain.models;

import com.example.domain.value_object.Address;
import com.example.symptom.Symptom;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "patients")
@Getter
@NoArgsConstructor
public class Patient {
        @Id
        @GeneratedValue
        Long id;
        String name;
        Integer age;
        String gender;
        String phoneNumber;
        Boolean isDiagnosed = false;
        @ManyToMany
        @JoinTable(
                name = "patient_symptoms",
                joinColumns = @JoinColumn(name = "patient_id"),
                inverseJoinColumns = @JoinColumn(name = "symptom_id")
        )
        private List<Symptom> symptoms;

        @Embedded
        Address address;

        String stringAddress;

        public Patient(String name, Integer age, String gender, String phoneNumber, String street, String city, Integer postalCode, String country) {
                this.name = name;
                this.age = age;
                this.gender = gender;
                this.phoneNumber = phoneNumber;
                stringAddress = street + ", " + city + ", " + postalCode + ", " + country;
                address = new Address(street, city, postalCode, country);

                this.symptoms = new ArrayList<Symptom>();
        }
        public void addSymptom(Symptom s)
        {
                if (symptoms == null)
                {
                        symptoms = new ArrayList<Symptom>();
                }
                if(!symptoms.contains(s))
                {
                        symptoms.add(s);
                }
        }

        public void addAddress(String streetName, String city, Integer postalCode, String country)
        {
                address = new Address(streetName, city, postalCode, country);
        }

        public String getName() {
                return name;
        }

        public Integer getAge() {
                return age;
        }

        public String getGender() {
                return gender;
        }

        public String getPhoneNumber() {
                return phoneNumber;
        }

        public List<Symptom> getSymptoms() {
                return symptoms;
        }

        public void setName(String name) {
                this.name = name;
        }

        public void setAge(Integer age) {
                this.age = age;
        }

        public void setGender(String gender) {
                this.gender = gender;
        }

        public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
        }

        public void setSymptoms(List<Symptom> symptoms) {
                this.symptoms = symptoms;
        }

        public Boolean getDiagnosed() {
                return isDiagnosed;
        }

        public void setDiagnosed(Boolean diagnosed) {
                isDiagnosed = diagnosed;
        }

        public String getAddress() {
                return address.toString();
        }

        public void setStreet(String street) {
                address.setStreet(street);
        }

        public void setCity(String city) {
                address.setCity(city);
        }

        public void setPostalCode(Integer postalCode) {
                address.setPostalCode(postalCode);
        }

        public void setCountry(String country) {
                address.setCountry(country);
        }

        public void setStringAddress(String street, String city, Integer postalCode, String country) {
                stringAddress = street + ", " + city + ", " + postalCode + ", " + country;
        }
}
