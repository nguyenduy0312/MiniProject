package com.example.employeemanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Employee createEmployee(Employee employee) {
        prepareDepartment(employee);

        return employeeRepository.save(employee);
    }

    public List<Employee> searchEmployees(String name, String department) {
        if (StringUtils.hasText(name)) {
            return employeeRepository.findByFullNameContainingIgnoreCase(name);
        }

        if (StringUtils.hasText(department)) {
            return employeeRepository.findByDepartment_NameContainingIgnoreCase(department);
        }

        return getAllEmployees();
    }

    private void prepareDepartment(Employee employee) {
        Department department = employee.getDepartment();

        if (department == null || !StringUtils.hasText(department.getName())) {
            employee.setDepartment(null);
            return;
        }

        String departmentName = department.getName().trim();
        Department savedDepartment = departmentRepository.findByNameIgnoreCase(departmentName)
                .orElseGet(() -> departmentRepository.save(new Department(null, departmentName)));

        employee.setDepartment(savedDepartment);
    }
}
