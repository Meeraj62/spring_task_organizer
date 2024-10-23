package com.prithak.taskorganizer.controller;

import com.prithak.taskorganizer.dto.LoginRequest;
import com.prithak.taskorganizer.dto.RegisterRequest;
import com.prithak.taskorganizer.service.UserAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthController {

    private UserAuthService userAuthService;

//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
//        return ResponseEntity.ok(userAuthService.registerUser(registerRequest));
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        return ResponseEntity.ok(userAuthService.loginUser(loginRequest));
//    }
}
