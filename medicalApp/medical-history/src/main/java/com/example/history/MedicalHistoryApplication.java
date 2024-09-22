package com.example.history;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.history"})
@EntityScan(basePackages = {"com.example.history.model"})
@EnableJpaRepositories(basePackages = {"com.example.history.repository"})
@ServletComponentScan
public class MedicalHistoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalHistoryApplication.class, args);
    }
}
