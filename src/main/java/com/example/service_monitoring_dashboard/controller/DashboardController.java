package com.example.service_monitoring_dashboard.controller;

import com.example.service_monitoring_dashboard.model.ServiceStatusInfo;
import com.example.service_monitoring_dashboard.service.ServiceMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {
    @Autowired
    private ServiceMonitorService serviceMonitorService;

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        List<ServiceStatusInfo> statusList = serviceMonitorService.checkServiceStatus();
        model.addAttribute("statusMap", statusList);
        return "dashboard";
    }

    @GetMapping("/history")
    public String getHistory(Model model) {
        Map<String, ServiceStatusInfo> historyMap = serviceMonitorService.getAllServiceStatuses();
        model.addAttribute("historyMap", historyMap);
        return "history";
    }


}

