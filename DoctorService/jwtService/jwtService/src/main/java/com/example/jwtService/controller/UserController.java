package com.example.jwtService.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.jwtService.Dto.LoginRequest;
import com.example.jwtService.Dto.RegisterRequest;
import com.example.jwtService.Service.JwtService;
import com.example.jwtService.Service.UserService;
import com.example.jwtService.structure.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.thirdparty.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    // Register new user
 
 // Controller expects this Content-Type
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RegisterRequest> registerUser(
            @RequestPart("user") String userJson, // JSON as String
            @RequestPart(value = "profilePictures", required = false) MultipartFile[] profilePictures
    ) throws JsonProcessingException, com.fasterxml.jackson.core.JsonProcessingException { // Removed the duplicate exception import

        // Convert JSON string to DTO
        ObjectMapper mapper = new ObjectMapper();
        RegisterRequest registerRequest = mapper.readValue(userJson, RegisterRequest.class);
        registerRequest.setProfilePictures(profilePictures);  // Set uploaded files in DTO
        RegisterRequest response = userService.registerUser(registerRequest);  // Call service to register user

        return ResponseEntity.ok(response);
    }



    // Get all users
    @GetMapping
    public ResponseEntity<List<RegisterRequest>> getAllUsers() {
        List<RegisterRequest> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    
    }

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<String>>patientLogin(@RequestBody LoginRequest logindto){
		ApiResponse<String > response=new ApiResponse<>();
		UsernamePasswordAuthenticationToken  token=new UsernamePasswordAuthenticationToken(logindto.email(),logindto.password());
	
		org.springframework.security.core.Authentication authentication=authenticationManager.authenticate(token);
	
		
		if(authentication.isAuthenticated()) {
			String jwtToken = jwtService.generateToken(logindto.email(),
					authentication.getAuthorities().iterator().next().getAuthority());
			
			response.setMessage("Welcome "+logindto.email());
			response.setSuccess(true);
			response.setData(jwtToken);
			 return ResponseEntity.ok(response);
		}
		 else {
             response.setMessage("Authentication failed");
             response.setSuccess(false);
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
         }
		
	}
	
	
	
	}


