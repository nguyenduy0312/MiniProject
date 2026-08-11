package com.example.employeemanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;

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

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);

        return ResponseEntity.ok(employee);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department
    ) {
        List<Employee> employees = employeeService.searchEmployees(name, department);

        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(employees);
    }

    // @PostMapping handles HTTP POST requests.
    @PostMapping
    public ResponseEntity<Employee> createEmployee(
            // @RequestBody maps the JSON request body to a Java object.
            @Valid @RequestBody Employee employee
    ) {
        Employee createdEmployee = employeeService.createEmployee(employee);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }
}
