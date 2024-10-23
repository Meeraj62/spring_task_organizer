package com.prithak.taskorganizer.service;

import com.prithak.taskorganizer.dto.RegisterRequestDTO;
import com.prithak.taskorganizer.entity.User;
import com.prithak.taskorganizer.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserAuthService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public void registerUser(RegisterRequestDTO request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
    }
}
