package com.example.demo.controller;

import com.example.demo.client.UserClient;
import com.example.demo.client.DoctorClient;
import com.example.demo.dto.CreateAppointmentRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.dto.DoctorResponse;
import com.example.demo.entity.Appointment;
import com.example.demo.service.AppointmentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/appointments") // ✅ uniform API gateway routing
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final UserClient userClient;
    private final DoctorClient doctorClient;

    public AppointmentController(AppointmentService appointmentService, UserClient userClient, DoctorClient doctorClient) {
        this.appointmentService = appointmentService;
        this.userClient = userClient;
        this.doctorClient = doctorClient;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateAppointmentRequest req,
                                    @RequestHeader(value = "X-User-Id", required = false) Long userId,
                                    @RequestHeader(value = "X-User-Email", required = false) String userEmail,
                                    Principal principal) {

        // ✅ Resolve authenticated user
        if (userId == null && principal != null) {
            userEmail = principal.getName();
        }

        if (userId == null && userEmail != null) {
            UserResponse u = null;
            try { u = userClient.getUserByEmail(userEmail); } catch (Exception ignored) {}

            if (u == null) return ResponseEntity.badRequest().body("User not found");
            userId = u.getId();
        }

        if (userId == null) return ResponseEntity.status(401).body("Unauthorized");

        // ✅ Validate doctor exists
        DoctorResponse doctor = null;
        try { doctor = doctorClient.getDoctorById(req.getDoctorId()); } catch (Exception ignored) {}

        if (doctor == null || doctor.getId() == null) {
            return ResponseEntity.badRequest().body("Invalid doctor ID");
        }

        // ✅ Create appointment
        Appointment appt = new Appointment();
        appt.setDoctorId(req.getDoctorId());
        appt.setUserId(userId);
        appt.setStartTime(req.getStartTime());
        appt.setEndTime(req.getEndTime());

        Appointment saved = appointmentService.create(appt);

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
