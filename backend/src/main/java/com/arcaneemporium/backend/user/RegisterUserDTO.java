package com.arcaneemporium.backend.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserDTO(

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 20)
        String username,
        @NotBlank(message = "Email is required")
        @Email(message = "Incorrect email address")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 5, max = 255, message = "Password must have 5-255 characters")
        String password
        ) {
}
