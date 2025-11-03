package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.DoctorClient;
import com.example.demo.client.UserClient;
import com.example.demo.dto.AppointmentEvent;
import com.example.demo.dto.DoctorResponse;
import com.example.demo.dto.UserResponse;
import com.example.demo.entity.Appointment;
import com.example.demo.kafka.AppointmentEventProducer;
import com.example.demo.repository.AppointmentRepository;

import jakarta.transaction.Transactional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired 
    private UserClient userClient;
    
    @Autowired 
    private DoctorClient doctorClient;
    
    @Autowired 
    private AppointmentEventProducer producer;

    public Appointment bookAppointment(Appointment appointment) {
        
        // ✅ Check if user exists
        UserResponse user = userClient.getUserById(appointment.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found!");
        }

        // ✅ Only patient can book
        if (!"ROLE_PATIENT".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException("Only patients can book appointments!");
        }

        // ✅ Check doctor exists
        DoctorResponse doctor = doctorClient.getDoctorById(appointment.getDoctorId());
        if (doctor == null) {
            throw new RuntimeException("Doctor not found!");
        }

        // ✅ Check doctor availability
        boolean isAvailable = doctorClient.isDoctorAvailable(appointment.getDoctorId());
        if (!isAvailable) {
            throw new RuntimeException("Doctor not available at selected time!");
        }

        // ✅ Save appointment
        appointment.setStatus("PENDING");
        Appointment savedAppt = appointmentRepository.save(appointment);

        // ✅ Publish Kafka event
        AppointmentEvent event = new AppointmentEvent(
                savedAppt.getId(),
                savedAppt.getUserId(),
                savedAppt.getDoctorId(),
                savedAppt.getStartTime(),
                user.getPhoneNumber(),
                "Your appointment has been booked successfully!"
        );

        producer.sendAppointmentEvent(event);

        return savedAppt;
    }

    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }

    public List<Appointment> findByUserId(Long userId) {
        return appointmentRepository.findByUserIdOrderByStartTimeDesc(userId);
    }

    public List<Appointment> findByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorIdOrderByStartTimeDesc(doctorId);
    }

    @Transactional
    public Appointment updateStatus(Long id, String status) {
        var appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment Not Found"));
        
        appt.setStatus(status);
        return appointmentRepository.save(appt);
    }
}
