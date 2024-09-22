package com.example.history.repository;

import com.example.history.model.MedicalCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalConditionRepository extends JpaRepository<MedicalCondition, Long> {
}
