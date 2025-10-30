package com.example.demo.dto;

import java.util.List;

public class DoctorResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private Double fee;
    private List<String> profilePictureUrls;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public Double getFee() { return fee; }
    public void setFee(Double fee) { this.fee = fee; }
    public List<String> getProfilePictureUrls() { return profilePictureUrls; }
    public void setProfilePictureUrls(List<String> profilePictureUrls) { this.profilePictureUrls = profilePictureUrls; }
}
