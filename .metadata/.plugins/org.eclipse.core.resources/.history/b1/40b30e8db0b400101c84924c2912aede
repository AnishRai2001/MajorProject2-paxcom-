package com.example.demo;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Appointment;
import com.example.demo.repository.AppointmentRepository;

import jakarta.transaction.Transactional;

public class AppointmentService {

	private AppointmentRepository appointmentRepository;
	
	@Transactional
    public Appointment create(Appointment appointment) {
        appointment.setStatus("PENDING");
        return appointmentRepository.save(appointment);
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
	        var ap = appointmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found"));
	        ap.setStatus(status);
	        return appointmentRepository.save(ap);
	    }

}
