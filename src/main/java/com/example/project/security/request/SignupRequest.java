package com.example.project.security.request;

import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank(message = "User name cannot be blank.")
    @Size(min = 3, max = 20, message = "Length of the user name must be in the range 3 to 20.")
    private String username;

    @NotBlank(message = "Email cannot be blank.")
    @Size(max = 50, message = "Maximum length for email is 50 characters.")
    @Email
    private String email;

    private Set<String> role;

    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 8, max = 40, message = "Length of the password must be in the range of 8 to 40.")
    private String password;

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}