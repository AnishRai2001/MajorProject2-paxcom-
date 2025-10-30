package com.example.demo.dto;



import java.time.ZonedDateTime;

public class AppointmentResponse {
    private Long id;
    private Long userId;
    private Long doctorId;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String status;

    // ctor + getters/setters
    public AppointmentResponse() {}
    public AppointmentResponse(Long id, Long userId, Long doctorId, ZonedDateTime startTime, ZonedDateTime endTime, String status) {
        this.id = id; this.userId = userId; this.doctorId = doctorId; this.startTime = startTime; this.endTime = endTime; this.status = status;
    }
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getDoctorId() { return doctorId; }
    public ZonedDateTime getStartTime() { return startTime; }
    public ZonedDateTime getEndTime() { return endTime; }
    public String getStatus() { return status; }
}

