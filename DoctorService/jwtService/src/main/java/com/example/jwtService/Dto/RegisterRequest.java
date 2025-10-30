package com.example.jwtService.Dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String role; // optional, can be null
    private MultipartFile[] profilePictures;
    private List<String> profilePictureUrls;

    
    public List<String> getProfilePictureUrls() {
		return profilePictureUrls;
	}
	public void setProfilePictureUrls(List<String> profilePictureUrls) {
		this.profilePictureUrls = profilePictureUrls;
	}
	// Getters & Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
	public MultipartFile[] getProfilePictures() {
		return profilePictures;
	}
	public void setProfilePictures(MultipartFile[] profilePictures) {
		this.profilePictures = profilePictures;
	}

    
    
}
