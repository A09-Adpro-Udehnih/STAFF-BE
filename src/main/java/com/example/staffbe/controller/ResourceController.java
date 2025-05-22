package com.example.staffbe.controller;

import com.example.staffbe.model.Refund;
import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.model.Payment;
import com.example.staffbe.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
public class ResourceController {

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/api/resources/dashboard")
    public CompletableFuture<Map<String, Object>> getDashboardData() {
        CompletableFuture<List<Refund>> refundsFuture = resourceService.getAllRefundsAsync();
        CompletableFuture<List<TutorApplication>> tutorsFuture = resourceService.getAllTutorApplicationsAsync();
        CompletableFuture<List<Payment>> paymentsFuture = resourceService.getAllPaymentsAsync();

        return CompletableFuture.allOf(refundsFuture, tutorsFuture, paymentsFuture)
                .thenApply(v -> {
                    try {
                        return Map.of(
                            "refunds", refundsFuture.get(),
                            "tutorApplications", tutorsFuture.get(),
                            "payments", paymentsFuture.get()
                        );
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to fetch dashboard data", e);
                    }
                });
    }
}
