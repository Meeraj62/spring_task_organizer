package com.prithak.taskorganizer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @PostMapping("/register")
    public void register() {

    }

    @PostMapping("/login")
    public void login() {

    }
}