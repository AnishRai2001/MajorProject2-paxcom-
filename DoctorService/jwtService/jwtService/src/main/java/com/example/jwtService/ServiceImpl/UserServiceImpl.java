package com.example.jwtService.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.jwtService.Dto.RegisterRequest;
import com.example.jwtService.Entity.User;
import com.example.jwtService.Service.S3Service;
import com.example.jwtService.Service.UserService;
import com.example.jwtService.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private S3Service s3Service;
    
    @Override
    public RegisterRequest registerUser(RegisterRequest registerRequest) {
        // Check email duplication
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("User already registered with email: " + registerRequest.getEmail());
        }

        // Create User entity and copy properties
        User user = new User();
        BeanUtils.copyProperties(registerRequest, user);

        // Encode password
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // Assign default role
        if (registerRequest.getRole() == null || registerRequest.getRole().isEmpty()) {
            user.setRole("ROLE_PATIENT"); // Default role
        } else {
            user.setRole(registerRequest.getRole());
        }
        
        MultipartFile[] files = registerRequest.getProfilePictures();
        if (files != null && files.length > 0) {
            List<String> urls = s3Service.uploadFile(files); // call your multi-file S3Service
            user.setProfilePictureUrls(urls);               // store URLs in user entity
        }

        
        

        // Save user
        User savedUser = userRepository.save(user);

        // Convert back to DTO
        RegisterRequest responseDto = new RegisterRequest();
        BeanUtils.copyProperties(savedUser, responseDto);
        responseDto.setPassword(null); // Hide password

        return responseDto;
    }

    @Override
    public List<RegisterRequest> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<RegisterRequest> dtoList = new ArrayList<>();

        for (User user : users) {
            RegisterRequest dto = new RegisterRequest();
            BeanUtils.copyProperties(user, dto);
            dto.setPassword(null); // Hide password
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
