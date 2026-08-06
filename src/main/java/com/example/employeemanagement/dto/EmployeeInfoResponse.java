package com.example.employeemanagement.dto;

public record EmployeeInfoResponse(
        String employeeCode,
        String formattedName,
        String encodedPassword
) {
}
