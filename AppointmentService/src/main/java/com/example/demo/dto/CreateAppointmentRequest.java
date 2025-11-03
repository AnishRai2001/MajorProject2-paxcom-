package com.example.demo.dto;

import java.time.ZonedDateTime;

public class CreateAppointmentRequest {
    private Long doctorId;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public ZonedDateTime getStartTime() { return startTime; }
    public void setStartTime(ZonedDateTime startTime) { this.startTime = startTime; }

    public ZonedDateTime getEndTime() { return endTime; }
    public void setEndTime(ZonedDateTime endTime) { this.endTime = endTime; }
}
