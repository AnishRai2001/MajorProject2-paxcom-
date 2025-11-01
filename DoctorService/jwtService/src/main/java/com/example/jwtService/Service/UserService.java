package com.example.jwtService.Service;

import java.util.List;
import com.example.jwtService.Dto.RegisterRequest;

public interface UserService {

    RegisterRequest registerUser(RegisterRequest registerRequest);

    List<RegisterRequest> getAllUsers();

    boolean existsByEmail(String email);

  
}
