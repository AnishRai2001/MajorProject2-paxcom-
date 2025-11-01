package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.demo.dto.AvailabilityDto;
import com.example.demo.dto.DoctorDto;

public interface DoctorService {
	DoctorDto registerDoctor(DoctorDto doctorDto);
	List<DoctorDto> getAllDoctor();
	  DoctorDto updateDoctor(Long id, DoctorDto dto);
	  void deleteDoctor(Long id);
	  List<DoctorDto> getAllDoctors(String specialization, String location);
	  List<AvailabilityDto> getAvailability(Long doctorId);
	  DoctorDto getDoctorById(Long id);
	  boolean isDoctorAvailable(Long id);

}
