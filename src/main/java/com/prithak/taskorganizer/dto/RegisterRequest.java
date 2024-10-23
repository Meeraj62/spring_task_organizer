package com.prithak.taskorganizer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}