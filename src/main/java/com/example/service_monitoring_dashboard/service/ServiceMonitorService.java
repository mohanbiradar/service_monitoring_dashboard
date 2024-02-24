package com.example.service_monitoring_dashboard.service;

import com.example.service_monitoring_dashboard.config.ServiceMonitorConfig;
import com.example.service_monitoring_dashboard.model.ServiceStatusInfo;
import com.example.service_monitoring_dashboard.repository.ServiceStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ServiceMonitorService {
    public static final String API_TEST_2 = "api-test2";
    @Autowired
    private ServiceMonitorConfig config;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10); // Adjust the pool size as needed

    @Autowired
    private ServiceStatusRepository serviceStatusRepository;


    @Async
    public CompletableFuture<List<ServiceStatusInfo>> checkServiceStatusAsync() {
        List<CompletableFuture<ServiceStatusInfo>> futures = new ArrayList<>();
        for (ServiceStatusInfo service : config.getServices()) {
            CompletableFuture<ServiceStatusInfo> future = CompletableFuture.supplyAsync(() -> {
                try {
                    log.info("Url: " + service.getUrl());
                    ResponseEntity<String> response = new RestTemplate().getForEntity(service.getUrl(), String.class);
                    log.info("Status: " + response.getStatusCode());
                    boolean isUp = response.getStatusCode().is2xxSuccessful();
                    return new ServiceStatusInfo(service.getName(), service.getUrl(), isUp, getEnvironment(service));
                } catch (HttpClientErrorException.Unauthorized unauthorized) {
                    log.warn("Unauthorized Error while calling health endpoint", unauthorized);
                    return new ServiceStatusInfo(service.getName(), service.getUrl(), true, getEnvironment(service));
                } catch (Exception e) {
                    log.error("Error while calling health endpoint", e);
                    return new ServiceStatusInfo(service.getName(), service.getUrl(), false, getEnvironment(service));
                }
            }, executorService);
            futures.add(future);
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        return allOf.thenApply(v ->
                futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );
    }

    private String getEnvironment(ServiceStatusInfo service) {
        return service.getUrl().contains(API_TEST_2) ? "test" : "prod";
    }

    public List<ServiceStatusInfo> checkServiceStatus() {
        try {
            List<ServiceStatusInfo> serviceStatuses = checkServiceStatusAsync().get();
            for (ServiceStatusInfo status : serviceStatuses) {
                serviceStatusRepository.save(status);
            }
            return serviceStatuses;
        } catch (InterruptedException | ExecutionException e) {
            // Handle exceptions
            return Collections.emptyList();
        }
    }

    public Map<String, ServiceStatusInfo> getAllServiceStatuses() {
        return serviceStatusRepository.getAll();
    }

    public void addService(ServiceStatusInfo serviceStatusInfo) {
        config.getServices().add(serviceStatusInfo);
    }

    public List<ServiceStatusInfo> getServices() {
        return config.getServices();
    }
}


