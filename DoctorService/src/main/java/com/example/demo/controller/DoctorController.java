package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.AvailabilityDto;
import com.example.demo.dto.DoctorDto;
import com.example.demo.service.DoctorService;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*") // optional: allows frontend access
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // ✅ 1. Register new doctor
    @PostMapping("/register")
    public ResponseEntity<DoctorDto> registerDoctor(@RequestBody DoctorDto doctorDto) {
        DoctorDto saved = doctorService.registerDoctor(doctorDto);
        return ResponseEntity.ok(saved);
    }

    // ✅ 2. Get all doctors
    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        List<DoctorDto> doctors = doctorService.getAllDoctor();
        return ResponseEntity.ok(doctors);
    }

    // ✅ 3. Get doctor by ID
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long id) {
        DoctorDto doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctor);
    }

    // ✅ 4. Update doctor
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@PathVariable Long id, @RequestBody DoctorDto doctorDto) {
        DoctorDto updated = doctorService.updateDoctor(id, doctorDto);
        return ResponseEntity.ok(updated);
    }

    // ✅ 5. Delete doctor
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted successfully.");
    }

    // ✅ 6. Search doctor by specialization or location
    @GetMapping("/search")
    public ResponseEntity<List<DoctorDto>> searchDoctors(
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String location) {
        List<DoctorDto> doctors = doctorService.getAllDoctors(specialization, location);
        return ResponseEntity.ok(doctors);
    }

    // ✅ 7. Get doctor availability
    @GetMapping("/{doctorId}/availability")
    public ResponseEntity<List<AvailabilityDto>> getDoctorAvailability(@PathVariable Long doctorId) {
        List<AvailabilityDto> availability = doctorService.getAvailability(doctorId);
        return ResponseEntity.ok(availability);
    }
}
