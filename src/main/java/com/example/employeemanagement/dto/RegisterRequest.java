package com.example.employeemanagement.dto;

import com.example.employeemanagement.entity.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotBlank(message = "Username must not be blank")
        String username,

        @NotBlank(message = "Password must not be blank")
        String password,

        @NotNull(message = "Role must not be null")
        Role role
) {
}
