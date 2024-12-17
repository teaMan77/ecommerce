package com.example.project.security.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "User name must not be blank.")
    @Min(value = 3, message = "User name length must be at least 3 characters.")
    private String username;

    @NotBlank(message = "Password cannot be blank.")
    @Min(value = 8, message = "Password length must be at least 8 characters.")
    private String password;
}