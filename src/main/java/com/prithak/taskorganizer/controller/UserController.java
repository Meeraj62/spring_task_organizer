package com.prithak.taskorganizer.controller;

import com.prithak.taskorganizer.entity.User;
import com.prithak.taskorganizer.security.JwtUtil;
import com.prithak.taskorganizer.service.UserService;
import com.prithak.taskorganizer.dto.AuthRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserController(UserService userInfoService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userInfoService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User savedUser = userService.saveUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected Error Occured", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            System.out.println("Login Successful for user: " + authRequest.getUsername());

            // If authentication is successful, generate the JWT token
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(jwtToken);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}

