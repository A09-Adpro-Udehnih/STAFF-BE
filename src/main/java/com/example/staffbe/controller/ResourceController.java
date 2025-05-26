package com.example.staffbe.controller;

import com.example.staffbe.dto.GlobalResponse;
import com.example.staffbe.dto.StaffDashboardResponse;
import com.example.staffbe.model.Payment;
import com.example.staffbe.model.Refund;
import com.example.staffbe.enums.Role;
import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.model.User;
import com.example.staffbe.service.ResourceService;
import com.example.staffbe.service.UserServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
public class ResourceController {

    private final ResourceService resourceService;
    private final UserServiceImpl userService;

    public ResourceController(ResourceService resourceService, UserServiceImpl userService) {
        this.resourceService = resourceService;
        this.userService = userService;
    }

    @GetMapping("/resources/dashboard")
    public CompletableFuture<ResponseEntity<GlobalResponse<StaffDashboardResponse>>> getDashboardData(Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        Optional<User> userOpt = userService.getUserById(userId);

        if (userOpt.isEmpty() || userOpt.get().getRole() != Role.STAFF) {
            GlobalResponse<StaffDashboardResponse> response = new GlobalResponse<>(
                    HttpStatus.FORBIDDEN,
                    false,
                    "Access denied. Only STAFF users can access dashboard.",
                    null
            );
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body(response));
        }

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
