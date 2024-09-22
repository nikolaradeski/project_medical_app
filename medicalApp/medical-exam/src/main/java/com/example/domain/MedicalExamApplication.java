package com.example.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.example.domain.models", "com.example.symptom"})
@EnableJpaRepositories(basePackages = {"com.example.domain.repositories", "com.example.symptom"})
@ServletComponentScan
public class MedicalExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalExamApplication.class, args);
    }
}
