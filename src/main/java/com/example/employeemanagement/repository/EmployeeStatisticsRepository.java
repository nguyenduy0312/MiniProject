package com.example.employeemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.employeemanagement.dto.DepartmentEmployeeStatisticsResponse;
import com.example.employeemanagement.entity.Employee;

public interface EmployeeStatisticsRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT COUNT(e) FROM Employee e")
    long countTotalEmployees();

    @Query("SELECT new com.example.employeemanagement.dto.DepartmentEmployeeStatisticsResponse(d.name, COUNT(e)) " +
            "FROM Employee e JOIN e.department d GROUP BY d.name ORDER BY d.name ASC")
    List<DepartmentEmployeeStatisticsResponse> getEmployeeCountByDepartment();
}
