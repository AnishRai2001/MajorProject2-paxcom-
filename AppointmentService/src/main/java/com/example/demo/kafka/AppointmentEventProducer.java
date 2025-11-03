package com.example.demo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AppointmentEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AppointmentEventProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "appointment-events";

    public void sendAppointmentEvent(AppointmentEvent event) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String value = mapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, value);
            System.out.println("✅ Event sent to Kafka: " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

