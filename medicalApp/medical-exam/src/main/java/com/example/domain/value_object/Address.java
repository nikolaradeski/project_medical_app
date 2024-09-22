package com.example.domain.value_object;

import com.example.base.ValueObject;
import jakarta.persistence.*;
@Embeddable
public class Address implements ValueObject {
    String street;
    String city;
    Integer postalCode;
    String country;
    String toString;
    public Address() {
    }

    public Address(String street, String city, Integer postalCode, String country) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.toString = street + ", " + city + " " + postalCode + ", " + country;
    }

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

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
