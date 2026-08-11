package com.example.employeemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.employeemanagement.service.EmployeeService;

@Controller
public class EmployeeViewController {

    private final EmployeeService employeeService;

    public EmployeeViewController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees/list")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());

        return "employees/list";
    }
}
