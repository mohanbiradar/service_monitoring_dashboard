package com.example.service_monitoring_dashboard;

import com.example.service_monitoring_dashboard.config.ServiceMonitorConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class ServiceMonitoringDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceMonitoringDashboardApplication.class, args);
	}

}
