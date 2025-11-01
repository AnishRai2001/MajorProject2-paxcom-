package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AvailabilityDto {
    private Long id;
    private Long doctorId;   // ✅ added
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean available; // ✅ added for availability check

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
