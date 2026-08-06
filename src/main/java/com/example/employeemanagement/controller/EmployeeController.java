package com.example.employeemanagement.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeemanagement.dto.EmployeeInfoResponse;
import com.example.employeemanagement.service.UtilityService;

@RestController
public class EmployeeController {

    private final UtilityService utilityService;
    private final PasswordEncoder passwordEncoder;

    // IoC: Spring manages object creation and supplies required dependencies.
    // Dependency Injection: required beans are provided from outside this class.
    // Constructor Injection: dependencies are declared in the constructor.
    public EmployeeController(UtilityService utilityService, PasswordEncoder passwordEncoder) {
        this.utilityService = utilityService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/employee/info")
    public EmployeeInfoResponse getEmployeeInfo() {
        String employeeCode = utilityService.generateEmployeeCode();
        String formattedName = utilityService.formatName(" Nguyen Van A ");
        String encodedPassword = passwordEncoder.encode("password123");

        return new EmployeeInfoResponse(employeeCode, formattedName, encodedPassword);
    }
}
