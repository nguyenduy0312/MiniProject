package com.example.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.employeemanagement.entity.Employee;

public interface EmployeeStatisticsRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT COUNT(e) FROM Employee e")
    long countTotalEmployees();
}
