package com.example.staffbe.service;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;

import com.example.staffbe.strategy.ApprovalContext;

class ApprovalServiceTest {

    @Mock
    private ApprovalContext approvalContext;

    @InjectMocks
    private ApprovalService approvalService;

    private UUID id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        id = UUID.randomUUID();
    }

    @Test
    void testApproveTutorApplication() {
        // Arrange
        String type = "tutor";
        doNothing().when(approvalContext).approve(id, type);

        // Act
        approvalService.approve(id, type);

        // Assert
        verify(approvalContext, times(1)).approve(id, type);
    }

    @Test
    void testRejectTutorApplication() {
        // Arrange
        String type = "tutor";
        doNothing().when(approvalContext).reject(id, type);

        // Act
        approvalService.reject(id, type);

        // Assert
        verify(approvalContext, times(1)).reject(id, type);
    }

    @Test
    void testApproveRefund() {
        // Arrange
        String type = "refund";
        doNothing().when(approvalContext).approve(id, type);

        // Act
        approvalService.approve(id, type);

        // Assert
        verify(approvalContext, times(1)).approve(id, type);
    }

    @Test
    void testRejectRefund() {
        // Arrange
        String type = "refund";
        doNothing().when(approvalContext).reject(id, type);

        // Act
        approvalService.reject(id, type);

        // Assert
        verify(approvalContext, times(1)).reject(id, type);
    }
    
    @Test
    void testApproveWithInvalidType() {
        // Arrange
        String type = "invalid";
        // This test will pass as long as the approvalContext handles the invalid type
        // We're just verifying that the service correctly passes the call to the context
        
        // Act
        approvalService.approve(id, type);
        
        // Assert
        verify(approvalContext, times(1)).approve(id, type);
    }
    
    @Test
    void testRejectWithInvalidType() {
        // Arrange
        String type = "invalid";
        // This test will pass as long as the approvalContext handles the invalid type
        // We're just verifying that the service correctly passes the call to the context
        
        // Act
        approvalService.reject(id, type);
        
        // Assert
        verify(approvalContext, times(1)).reject(id, type);
    }
}