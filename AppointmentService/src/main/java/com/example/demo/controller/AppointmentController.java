package com.example.demo.controller;

import com.example.demo.client.UserClient;
import com.example.demo.client.DoctorClient;
import com.example.demo.dto.CreateAppointmentRequest;
import com.example.demo.dto.DoctorResponse;
import com.example.demo.entity.Appointment;
import com.example.demo.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorClient doctorClient;

    public AppointmentController(AppointmentService appointmentService, DoctorClient doctorClient) {
        this.appointmentService = appointmentService;
        this.doctorClient = doctorClient;
    }
    @PostMapping
    public ResponseEntity<?> createAppointment(
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            @RequestBody CreateAppointmentRequest request) {

        if (userId == null) {
            return ResponseEntity.badRequest().body("Missing X-User-Id header");
        }

        // ✅ Check doctor exists
        DoctorResponse doctor = doctorClient.getDoctorById(request.getDoctorId());
        if (doctor == null || doctor.getId() == null) {
            return ResponseEntity.badRequest().body("Invalid doctor ID");
        }

        Appointment appointment = new Appointment();
        appointment.setUserId(userId);
        appointment.setDoctorId(request.getDoctorId());
        appointment.setStartTime(request.getStartTime());
        appointment.setEndTime(request.getEndTime());

        Appointment saved = appointmentService.bookAppointment(appointment);

        return ResponseEntity.status(201).body(saved);
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return appointmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(appointmentService.findByUserId(userId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<?> getByDoctorId(@PathVariable Long doctorId) {
        return ResponseEntity.ok(appointmentService.findByDoctorId(doctorId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(appointmentService.updateStatus(id, status));
    }
}
