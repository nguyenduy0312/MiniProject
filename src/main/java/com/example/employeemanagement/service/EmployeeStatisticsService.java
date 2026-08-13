package com.example.employeemanagement.service;

import org.springframework.stereotype.Service;

import com.example.employeemanagement.dto.EmployeeStatisticsResponse;
import com.example.employeemanagement.repository.EmployeeStatisticsRepository;

@Service
public class EmployeeStatisticsService {

    private final EmployeeStatisticsRepository employeeStatisticsRepository;

    public EmployeeStatisticsService(EmployeeStatisticsRepository employeeStatisticsRepository) {
        this.employeeStatisticsRepository = employeeStatisticsRepository;
    }

    public EmployeeStatisticsResponse getTotalEmployees() {
        long totalEmployees = employeeStatisticsRepository.countTotalEmployees();
        return new EmployeeStatisticsResponse(totalEmployees);
    }
}
