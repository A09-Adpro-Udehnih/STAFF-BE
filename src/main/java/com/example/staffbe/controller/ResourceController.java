package com.example.staffbe.controller;

import com.example.staffbe.dto.GlobalResponse;
import com.example.staffbe.dto.StaffDashboardResponse;
import com.example.staffbe.model.Payment;
import com.example.staffbe.model.Refund;
import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.service.ResourceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class ResourceController {

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/resources/dashboard")
    public CompletableFuture<ResponseEntity<GlobalResponse<StaffDashboardResponse>>> getDashboardData() {
        CompletableFuture<List<Refund>> refundsFuture = resourceService.getAllRefundsAsync();
        CompletableFuture<List<TutorApplication>> tutorsFuture = resourceService.getAllTutorApplicationsAsync();
        CompletableFuture<List<Payment>> paymentsFuture = resourceService.getAllPaymentsAsync();

        return CompletableFuture.allOf(refundsFuture, tutorsFuture, paymentsFuture)
                .thenApply(v -> {
                    try {
                        StaffDashboardResponse dashboardData = new StaffDashboardResponse(
                                refundsFuture.get(),
                                paymentsFuture.get(),
                                tutorsFuture.get()
                        );

                        GlobalResponse<StaffDashboardResponse> response = new GlobalResponse<>(
                                HttpStatus.OK,
                                true,
                                "Dashboard data retrieved successfully",
                                dashboardData
                        );

                        return ResponseEntity.ok(response);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to fetch dashboard data", e);
                    }
                });
    }
}
