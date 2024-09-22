package com.example.symptom;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Entity
@Table(name = "symptoms")
@NoArgsConstructor
public class Symptom {
    @Id
    @GeneratedValue
    Long id;
    String name;

    public Symptom(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symptom symptom = (Symptom) o;
        return this.getId().equals(symptom.getId()) && name.equals(symptom.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), name);
    }
}
