package com.example.employeemanagement.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.employeemanagement.dto.DepartmentEmployeeStatisticsResponse;
import com.example.employeemanagement.repository.EmployeeStatisticsRepository;

@ExtendWith(MockitoExtension.class)
class EmployeeStatisticsServiceTest {

    @Mock
    private EmployeeStatisticsRepository employeeStatisticsRepository;

    @InjectMocks
    private EmployeeStatisticsService employeeStatisticsService;

    @Test
    void getEmployeeCountByDepartment_shouldReturnDepartmentStatistics() {
        List<DepartmentEmployeeStatisticsResponse> expected = List.of(
                new DepartmentEmployeeStatisticsResponse("IT", 5L),
                new DepartmentEmployeeStatisticsResponse("HR", 2L)
        );

        when(employeeStatisticsRepository.getEmployeeCountByDepartment()).thenReturn(expected);

        List<DepartmentEmployeeStatisticsResponse> result = employeeStatisticsService.getEmployeeCountByDepartment();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("IT", result.get(0).department());
        assertEquals(5L, result.get(0).employeeCount());
        assertEquals("HR", result.get(1).department());
        assertEquals(2L, result.get(1).employeeCount());
    }

    @Test
    void getStatistics_whenDatabaseHasNoEmployees_shouldReturnZeroAndEmptyList() {
        when(employeeStatisticsRepository.countTotalEmployees()).thenReturn(0L);
        when(employeeStatisticsRepository.getEmployeeCountByDepartment()).thenReturn(List.of());

        var total = employeeStatisticsService.getTotalEmployees();
        var byDepartment = employeeStatisticsService.getEmployeeCountByDepartment();

        assertNotNull(total);
        assertEquals(0L, total.totalEmployees());
        assertNotNull(byDepartment);
        assertEquals(0, byDepartment.size());
    }
}
