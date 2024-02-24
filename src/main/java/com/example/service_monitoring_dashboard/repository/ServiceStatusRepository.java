package com.example.service_monitoring_dashboard.repository;

import com.example.service_monitoring_dashboard.model.ServiceStatusInfo;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ServiceStatusRepository {

    private final Map<String, ServiceStatusInfo> serviceStatusMap = new ConcurrentHashMap<>();

    public void save(ServiceStatusInfo serviceStatusInfo) {
        serviceStatusMap.put(serviceStatusInfo.getName(), serviceStatusInfo);
    }

    public Map<String, ServiceStatusInfo> getAll() {
        return new ConcurrentHashMap<>(serviceStatusMap);
    }
}

