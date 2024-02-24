package com.example.service_monitoring_dashboard.config;

import com.example.service_monitoring_dashboard.model.ServiceStatusInfo;
import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@Component
@EnableAutoConfiguration
@EnableConfigurationProperties
@ConfigurationProperties()
public class ServiceMonitorConfig {
    List<ServiceStatusInfo> services;
}