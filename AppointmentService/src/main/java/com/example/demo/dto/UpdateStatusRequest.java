package com.example.demo.dto;

public class UpdateStatusRequest {
	// @NotBlank
	 
	    private String status; // CONFIRMED, CANCELLED

	    public String getStatus() { return status; }
	    public void setStatus(String status) { this.status = status; }
	}
