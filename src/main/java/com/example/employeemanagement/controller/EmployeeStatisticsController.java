package com.example.employeemanagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeemanagement.dto.DepartmentEmployeeStatisticsResponse;
import com.example.employeemanagement.dto.EmployeeStatisticsResponse;
import com.example.employeemanagement.service.EmployeeStatisticsService;

@RestController
@RequestMapping("/employees")
public class EmployeeStatisticsController {

    private final EmployeeStatisticsService employeeStatisticsService;

    public EmployeeStatisticsController(EmployeeStatisticsService employeeStatisticsService) {
        this.employeeStatisticsService = employeeStatisticsService;
    }

    @GetMapping("/statistics/count")
    public ResponseEntity<EmployeeStatisticsResponse> getTotalEmployees() {
        EmployeeStatisticsResponse response = employeeStatisticsService.getTotalEmployees();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/statistics/by-department")
    public ResponseEntity<List<DepartmentEmployeeStatisticsResponse>> getEmployeeCountByDepartment() {
        List<DepartmentEmployeeStatisticsResponse> response = employeeStatisticsService.getEmployeeCountByDepartment();
        return ResponseEntity.ok(response);
    }
}
