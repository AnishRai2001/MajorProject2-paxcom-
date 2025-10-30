package com.example.demo.client;

import com.example.demo.dto.DoctorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "doctor-service", path = "/api/doctors")
public interface DoctorClient {

    @GetMapping("/{id}")
    DoctorResponse getDoctorById(@PathVariable("id") Long id);
}
