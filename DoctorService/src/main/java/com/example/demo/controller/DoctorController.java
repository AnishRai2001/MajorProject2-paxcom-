package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.AvailabilityDto;
import com.example.demo.dto.DoctorDto;

import com.example.demo.service.DoctorService;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // ✅ Register doctor
    @PostMapping("/register")
    public ResponseEntity<DoctorDto> registerDoctor(@RequestBody DoctorDto doctorDto) {
        return ResponseEntity.ok(doctorService.registerDoctor(doctorDto));
    }

    // ✅ Get all doctors
    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctor());
    }

    // ✅ Get doctor by ID
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    // ✅ Update doctor
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@PathVariable Long id, @RequestBody DoctorDto doctorDto) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorDto));
    }

    // ✅ Delete doctor
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted successfully.");
    }

    // ✅ Search doctors
    @GetMapping("/search")
    public ResponseEntity<List<DoctorDto>> searchDoctors(
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String location) {

        return ResponseEntity.ok(doctorService.getAllDoctors(specialization, location));
    }

    // ✅ Get doctor's availability list
    @GetMapping("/{doctorId}/availability-list")
    public ResponseEntity<List<AvailabilityDto>> getDoctorAvailability(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.getAvailability(doctorId));
    }
 // ✅ Today availability
    @GetMapping("/{doctorId}/availability")
    public ResponseEntity<AvailabilityDto> isDoctorAvailable(@PathVariable Long doctorId) {
        boolean available = doctorService.isDoctorAvailable(doctorId);

        AvailabilityDto dto = new AvailabilityDto();
        dto.setDoctorId(doctorId);
        dto.setDate(LocalDate.now());
        dto.setAvailable(available);

        return ResponseEntity.ok(dto);
    }

    // ✅ Specific date availability
    @GetMapping("/{doctorId}/availability/{date}")
    public ResponseEntity<AvailabilityDto> isDoctorAvailableOnDate(
            @PathVariable Long doctorId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        boolean available = doctorService.isDoctorAvailable(doctorId);

        AvailabilityDto dto = new AvailabilityDto();
        dto.setDoctorId(doctorId);
        dto.setDate(date);
        dto.setAvailable(available);

        return ResponseEntity.ok(dto);
    }

}
