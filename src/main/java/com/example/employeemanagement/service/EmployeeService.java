package com.example.employeemanagement.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Cacheable(cacheNames = "employeeReports", key = "'employeeCount'")
    public long countEmployees() {
        logger.info("Querying database for employee count");

        return employeeRepository.count();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Employee createEmployee(Employee employee) {
        try {
            prepareDepartment(employee);

            Employee savedEmployee = employeeRepository.save(employee);
            logger.info(
                    "Created employee: employeeId={}, employeeCode={}, fullName={}",
                    savedEmployee.getId(),
                    savedEmployee.getEmployeeCode(),
                    savedEmployee.getFullName()
            );

            return savedEmployee;
        } catch (RuntimeException exception) {
            logger.error(
                    "Failed to create employee: employeeCode={}, fullName={}",
                    employee.getEmployeeCode(),
                    employee.getFullName(),
                    exception
            );
            throw exception;
        }
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Employee not found for update: id={}", id);
                    return new EmployeeNotFoundException(id);
                });

        try {
            employee.setEmployeeCode(employeeDetails.getEmployeeCode());
            employee.setFullName(employeeDetails.getFullName());
            employee.setEmail(employeeDetails.getEmail());
            employee.setDepartment(employeeDetails.getDepartment());
            prepareDepartment(employee);

            Employee updatedEmployee = employeeRepository.save(employee);
            logger.info(
                    "Updated employee: employeeId={}, employeeCode={}, fullName={}",
                    updatedEmployee.getId(),
                    updatedEmployee.getEmployeeCode(),
                    updatedEmployee.getFullName()
            );

            return updatedEmployee;
        } catch (RuntimeException exception) {
            logger.error("Failed to update employee: id={}", id, exception);
            throw exception;
        }
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Employee not found for delete: id={}", id);
                    return new EmployeeNotFoundException(id);
                });

        try {
            employeeRepository.delete(employee);
            logger.info(
                    "Deleted employee: employeeId={}, employeeCode={}, fullName={}",
                    employee.getId(),
                    employee.getEmployeeCode(),
                    employee.getFullName()
            );
        } catch (RuntimeException exception) {
            logger.error("Failed to delete employee: id={}", id, exception);
            throw exception;
        }
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
