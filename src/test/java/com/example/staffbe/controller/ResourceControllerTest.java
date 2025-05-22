package com.example.staffbe.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

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
        List<Refund> mockRefunds = List.of(new Refund());
        List<Payment> mockPayments = List.of(new Payment());
        List<TutorApplication> mockTutors = List.of(new TutorApplication());

        when(resourceService.getAllRefundsAsync()).thenReturn(CompletableFuture.completedFuture(mockRefunds));
        when(resourceService.getAllPaymentsAsync()).thenReturn(CompletableFuture.completedFuture(mockPayments));
        when(resourceService.getAllTutorApplicationsAsync()).thenReturn(CompletableFuture.completedFuture(mockTutors));

        Map<String, Object> result = resourceController.getDashboardData().get();

        assertEquals(mockRefunds, result.get("refunds"));
        assertEquals(mockPayments, result.get("payments"));
        assertEquals(mockTutors, result.get("tutorApplications"));

        verify(resourceService).getAllRefundsAsync();
        verify(resourceService).getAllPaymentsAsync();
        verify(resourceService).getAllTutorApplicationsAsync();
    }
}
