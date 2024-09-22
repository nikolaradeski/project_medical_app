package com.example.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "diagnosis")
@NoArgsConstructor
public class Diagnosis  {
    @Id
    @GeneratedValue
    Long id;
    String description;

    public Diagnosis(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
