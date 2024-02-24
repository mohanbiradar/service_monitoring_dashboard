package com.example.service_monitoring_dashboard.service;

import com.example.service_monitoring_dashboard.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class ScheduledService {
    @Autowired
    private ServiceMonitorService serviceMonitorService;

    @Scheduled(fixedRate = 1200000) // every 60 seconds
    public void checkServiceStatus() {
        List<ServiceStatusInfo> statusMap = serviceMonitorService.checkServiceStatus();
    }
}

