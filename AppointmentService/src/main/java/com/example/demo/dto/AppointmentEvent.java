package com.example.demo.dto;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class AppointmentEvent {
    private Long appointmentId;
    private Long userId;
    private Long doctorId;
    private ZonedDateTime startTime;
    private String phoneNumber;
    private String message;

    public AppointmentEvent(Long appointmentId, Long userId, Long doctorId, 
                            ZonedDateTime zonedDateTime, String phoneNumber, String message) {
        this.appointmentId = appointmentId;
        this.userId = userId;
        this.doctorId = doctorId;
        this.startTime = zonedDateTime;
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}



	public ZonedDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(ZonedDateTime startTime) {
		this.startTime = startTime;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    // ✅ Getters and Setters
    
}
