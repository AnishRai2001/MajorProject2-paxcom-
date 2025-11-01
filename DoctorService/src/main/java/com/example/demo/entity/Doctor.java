package com.example.demo.entity;



import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialization;
    private int experience;
    private String location;
    private String contactNumber;
    private boolean available = true;

    @Version
    private Long version; // optimistic locking

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Availability> availabilitySlots = new ArrayList<>();

    // Getters and setters
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

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public List<Availability> getAvailabilitySlots() { return availabilitySlots; }
    public void setAvailabilitySlots(List<Availability> availabilitySlots) {
        this.availabilitySlots.clear();
        if (availabilitySlots != null) {
            this.availabilitySlots.addAll(availabilitySlots);
        }
    }

    public void addAvailability(Availability a) {
        a.setDoctor(this);
        this.availabilitySlots.add(a);
    }

    public void removeAvailability(Availability a) {
        a.setDoctor(null);
        this.availabilitySlots.remove(a);
    }
}
