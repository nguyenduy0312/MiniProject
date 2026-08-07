package com.example.employeemanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeemanagement.entity.Employee;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final List<Employee> employees = new ArrayList<>(List.of(
            new Employee(1L, "EMP001", "Nguyen Van A", "Engineering", "nguyenvana@example.com"),
            new Employee(2L, "EMP002", "Tran Thi B", "Human Resources", "tranthib@example.com"),
            new Employee(3L, "EMP003", "Le Van C", "Finance", "levanc@example.com")
    ));
    private final AtomicLong nextId = new AtomicLong(4L);

    @GetMapping
    public List<Employee> getEmployees() {
        return employees;
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        if (employee.getId() == null) {
            employee.setId(nextId.getAndIncrement());
        }

        employees.add(employee);
        return employee;
    }
}
