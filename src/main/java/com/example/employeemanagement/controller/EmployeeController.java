package com.example.employeemanagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.service.EmployeeService;

@RestController
// @RequestMapping defines the base URL path for all APIs in this controller.
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // @GetMapping handles HTTP GET requests.
    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();

        // ResponseEntity lets us control both response body and HTTP status.
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(employees);
    }

    // @PostMapping handles HTTP POST requests.
    @PostMapping
    public ResponseEntity<Employee> createEmployee(
            // @RequestBody maps the JSON request body to a Java object.
            @RequestBody Employee employee
    ) {
        Employee createdEmployee = employeeService.createEmployee(employee);

        return ResponseEntity.status(201).body(createdEmployee);
    }
}
