package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Appointment;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUserIdOrderByStartTimeDesc(Long userId);
    List<Appointment> findByDoctorIdOrderByStartTimeDesc(Long doctorId);
}

