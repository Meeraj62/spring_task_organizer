package com.prithak.taskorganizer.service;

import com.prithak.taskorganizer.dto.RegisterRequest;
import com.prithak.taskorganizer.entity.User;
import com.prithak.taskorganizer.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserAuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
}
