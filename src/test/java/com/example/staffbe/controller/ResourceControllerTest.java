package com.example.staffbe.controller;

import com.example.staffbe.dto.GlobalResponse;
import com.example.staffbe.dto.StaffDashboardResponse;
import com.example.staffbe.enums.Role;
import com.example.staffbe.model.Payment;
import com.example.staffbe.model.Refund;
import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.model.User;
import com.example.staffbe.service.ResourceService;
import com.example.staffbe.service.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ResourceControllerTest {

    @Mock
    private ResourceService resourceService;

    @Mock
    private UserServiceImpl userService;

    private ResourceController resourceController;

    private UUID mockUserId;
    private Principal principal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Simulasi principal
        mockUserId = UUID.randomUUID();
        principal = () -> mockUserId.toString();

        resourceController = new ResourceController(resourceService, userService);
    }

    @Test
    void testGetDashboardData_AsStaff() throws Exception {
        // Mock data dashboard
        List<Refund> mockRefunds = List.of(new Refund());
        List<Payment> mockPayments = List.of(new Payment());
        List<TutorApplication> mockTutors = List.of(new TutorApplication());

        // Mock user dengan role STAFF
        User staffUser = User.builder()
                .id(mockUserId)
                .role(Role.STAFF)
                .build();

        when(userService.getUserById(mockUserId)).thenReturn(Optional.of(staffUser));
        when(resourceService.getAllRefundsAsync()).thenReturn(CompletableFuture.completedFuture(mockRefunds));
        when(resourceService.getAllPaymentsAsync()).thenReturn(CompletableFuture.completedFuture(mockPayments));
        when(resourceService.getAllTutorApplicationsAsync()).thenReturn(CompletableFuture.completedFuture(mockTutors));

        CompletableFuture<ResponseEntity<GlobalResponse<StaffDashboardResponse>>> future = 
            resourceController.getDashboardData(principal);
        
        ResponseEntity<GlobalResponse<StaffDashboardResponse>> responseEntity = future.get();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        GlobalResponse<StaffDashboardResponse> response = responseEntity.getBody();
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("Dashboard data retrieved successfully", response.getMessage());

        StaffDashboardResponse dashboard = response.getData();
        assertNotNull(dashboard);
        assertEquals(mockRefunds, dashboard.getRefunds());
        assertEquals(mockPayments, dashboard.getPayments());
        assertEquals(mockTutors, dashboard.getTutorApplications());

        verify(resourceService).getAllRefundsAsync();
        verify(resourceService).getAllPaymentsAsync();
        verify(resourceService).getAllTutorApplicationsAsync();
    }

    @Test
    void testGetDashboardData_NotStaff() throws Exception {
        // Mock user bukan STAFF
        User nonStaffUser = User.builder()
                .id(mockUserId)
                .role(Role.STUDENT)
                .build();

        when(userService.getUserById(mockUserId)).thenReturn(Optional.of(nonStaffUser));

        CompletableFuture<ResponseEntity<GlobalResponse<StaffDashboardResponse>>> future =
                resourceController.getDashboardData(principal);

        ResponseEntity<GlobalResponse<StaffDashboardResponse>> response = future.get();
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Access denied. Only STAFF users can access dashboard.", response.getBody().getMessage());

        verify(resourceService, never()).getAllRefundsAsync();
    }

    @Test
    void testGetDashboardData_UserNotFound() throws Exception {
        when(userService.getUserById(mockUserId)).thenReturn(Optional.empty());

        CompletableFuture<ResponseEntity<GlobalResponse<StaffDashboardResponse>>> future =
                resourceController.getDashboardData(principal);

        ResponseEntity<GlobalResponse<StaffDashboardResponse>> response = future.get();
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Access denied. Only STAFF users can access dashboard.", response.getBody().getMessage());

        verify(resourceService, never()).getAllTutorApplicationsAsync();
    }
}
