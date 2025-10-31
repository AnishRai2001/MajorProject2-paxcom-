package com.example.demo.dto;



import java.util.ArrayList;
import java.util.List;

public class DoctorDto {
    private Long id;
    private String name;
    private String specialization;
    private int experience;
    private String location;
    private String contactNumber;
    private boolean available;
    private List<AvailabilityDto> availability = new ArrayList<>();

    // Getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public List<AvailabilityDto> getAvailability() { return availability; }
    public void setAvailability(List<AvailabilityDto> availability) { this.availability = availability; }
}

