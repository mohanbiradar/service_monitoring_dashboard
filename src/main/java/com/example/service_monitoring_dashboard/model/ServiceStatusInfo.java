package com.example.service_monitoring_dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceStatusInfo {
    private String name;
    private String url;
    private boolean isRunning;
    private String environment;
}
