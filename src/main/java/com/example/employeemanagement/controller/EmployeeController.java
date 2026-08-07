package com.example.employeemanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.entity.Employee;

@RestController
// @RequestMapping defines the base URL path for all APIs in this controller.
@RequestMapping("/employees")
public class EmployeeController {

    private final Department engineering = new Department(1L, "Engineering");
    private final Department humanResources = new Department(2L, "Human Resources");
    private final Department finance = new Department(3L, "Finance");

    private final List<Employee> employees = new ArrayList<>(List.of(
            new Employee(1L, "EMP001", "Nguyen Van A", "nguyenvana@example.com", engineering),
            new Employee(2L, "EMP002", "Tran Thi B", "tranthib@example.com", humanResources),
            new Employee(3L, "EMP003", "Le Van C", "levanc@example.com", finance)
    ));
    private final AtomicLong nextId = new AtomicLong(4L);

    // @GetMapping handles HTTP GET requests.
    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees() {
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
        if (employee.getId() == null) {
            employee.setId(nextId.getAndIncrement());
        }

        employees.add(employee);
        return ResponseEntity.status(201).body(employee);
    }
}
