package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.UserResponse;

@FeignClient(name = "jwtservice", path = "/api/users")
public interface UserClient {

    @GetMapping("/{id}")
    UserResponse getUserById(@PathVariable("id") Long id);
    
    @GetMapping("/email/{email}")
    UserResponse getUserByEmail(@PathVariable("email") String email);
}
