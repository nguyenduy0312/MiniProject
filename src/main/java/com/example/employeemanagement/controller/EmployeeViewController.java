package com.example.employeemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;

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

    @GetMapping("/employees/add")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", createEmployeeFormModel());

        return "employees/add";
    }

    @PostMapping("/employees/add")
    public String addEmployee(@Valid Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ensureDepartmentForForm(employee);
            return "employees/add";
        }

        employeeService.createEmployee(employee);

        return "redirect:/employees/list";
    }

    private Employee createEmployeeFormModel() {
        Employee employee = new Employee();
        employee.setDepartment(new Department());

        return employee;
    }

    private void ensureDepartmentForForm(Employee employee) {
        if (employee.getDepartment() == null) {
            employee.setDepartment(new Department());
        }
    }
}
