package com.example.employeemanagement.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service // Marks this class as a Spring Bean managed by the application context.
public class UtilityService {

    private final AtomicInteger employeeCodeSequence = new AtomicInteger(0);

    public String formatName(String name) {
        if (name == null) {
            return null;
        }

        return name.trim().toUpperCase();
    }

    public String generateEmployeeCode() {
        int nextCode = employeeCodeSequence.incrementAndGet();
        return String.format("EMP%03d", nextCode);
    }
}
