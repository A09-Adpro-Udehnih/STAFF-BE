package com.example.staffbe.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.staffbe.dto.GlobalResponse;
import com.example.staffbe.dto.StaffDashboardResponse;
import com.example.staffbe.model.Payment;
import com.example.staffbe.model.Refund;
import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.service.ResourceService;

public class ResourceControllerTest {

    @Mock
    private ResourceService resourceService;

    private ResourceController resourceController;

    @BeforeEach
    void setUp() {
        openMocks(this);
        resourceController = new ResourceController(resourceService);
    }

    @Test
    void testGetDashboardData() throws Exception {
        // Mock data
        List<Refund> mockRefunds = List.of(new Refund());
        List<Payment> mockPayments = List.of(new Payment());
        List<TutorApplication> mockTutors = List.of(new TutorApplication());

        // Mock service
        when(resourceService.getAllRefundsAsync()).thenReturn(CompletableFuture.completedFuture(mockRefunds));
        when(resourceService.getAllPaymentsAsync()).thenReturn(CompletableFuture.completedFuture(mockPayments));
        when(resourceService.getAllTutorApplicationsAsync()).thenReturn(CompletableFuture.completedFuture(mockTutors));

        // Call controller
        ResponseEntity<GlobalResponse<StaffDashboardResponse>> responseEntity =
                resourceController.getDashboardData().get();

        // Validate response entity
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

        // Verify service calls
        verify(resourceService).getAllRefundsAsync();
        verify(resourceService).getAllPaymentsAsync();
        verify(resourceService).getAllTutorApplicationsAsync();
    }
}
