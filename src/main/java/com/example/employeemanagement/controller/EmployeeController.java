package com.example.employeemanagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeemanagement.entity.Employee;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final List<Employee> employees = List.of(
            new Employee(1L, "EMP001", "Nguyen Van A", "Engineering", "nguyenvana@example.com"),
            new Employee(2L, "EMP002", "Tran Thi B", "Human Resources", "tranthib@example.com"),
            new Employee(3L, "EMP003", "Le Van C", "Finance", "levanc@example.com")
    );

    @GetMapping
    public List<Employee> getEmployees() {
        return employees;
    }
}
