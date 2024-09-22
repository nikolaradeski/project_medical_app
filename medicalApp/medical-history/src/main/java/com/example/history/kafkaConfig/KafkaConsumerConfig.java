package com.example.history.kafkaConfig;

import com.example.events.AddDiagnosisToMedicalHistory;
import com.example.events.AddPrescriptionToMedicalHistory;
import com.example.events.AddSymptomToMedicalHistory;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;


import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Bean
    public ConsumerFactory<String, AddSymptomToMedicalHistory> consumerFactory1() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "symptom-group");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), new JsonDeserializer<>(AddSymptomToMedicalHistory.class));
    }

    @Bean
    public ConsumerFactory<String, AddDiagnosisToMedicalHistory> consumerFactory2() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "diagnosis-group");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), new JsonDeserializer<>(AddDiagnosisToMedicalHistory.class));
    }

    @Bean
    public ConsumerFactory<String, AddPrescriptionToMedicalHistory> consumerFactory3() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "prescription-group");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), new JsonDeserializer<>(AddPrescriptionToMedicalHistory.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AddSymptomToMedicalHistory> symptomKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AddSymptomToMedicalHistory> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory1());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AddDiagnosisToMedicalHistory> diagnosisKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AddDiagnosisToMedicalHistory> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory2());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AddPrescriptionToMedicalHistory> prescriptionKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AddPrescriptionToMedicalHistory> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory3());
        return factory;
    }
}

