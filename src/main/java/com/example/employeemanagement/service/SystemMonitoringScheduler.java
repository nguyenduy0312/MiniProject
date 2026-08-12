package com.example.employeemanagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SystemMonitoringScheduler {

    private static final Logger logger = LoggerFactory.getLogger(SystemMonitoringScheduler.class);

    @Scheduled(fixedRate = 30000)
    public void logSystemStatus() {
        logger.info("System running");
    }
}
