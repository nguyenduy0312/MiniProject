package com.example.employeemanagement.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.employeemanagement.dto.DepartmentEmployeeStatisticsResponse;
import com.example.employeemanagement.dto.EmployeeStatisticsResponse;
import com.example.employeemanagement.service.EmployeeStatisticsService;

@Controller
public class EmployeeStatisticsViewController {

    private final EmployeeStatisticsService employeeStatisticsService;

    public EmployeeStatisticsViewController(EmployeeStatisticsService employeeStatisticsService) {
        this.employeeStatisticsService = employeeStatisticsService;
    }

    @GetMapping("/employees/statistics")
    public String showStatistics(Model model) {
        EmployeeStatisticsResponse totalEmployees = employeeStatisticsService.getTotalEmployees();
        List<DepartmentEmployeeStatisticsResponse> departmentStatistics = employeeStatisticsService.getEmployeeCountByDepartment();

        model.addAttribute("totalEmployees", totalEmployees.totalEmployees());
        model.addAttribute("departmentStatistics", departmentStatistics);

        return "employees/statistics";
    }
}
