package com.example.employeemanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employeemanagement.dto.DepartmentEmployeeStatisticsResponse;
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

    public List<DepartmentEmployeeStatisticsResponse> getEmployeeCountByDepartment() {
        return employeeStatisticsRepository.getEmployeeCountByDepartment();
    }
}
