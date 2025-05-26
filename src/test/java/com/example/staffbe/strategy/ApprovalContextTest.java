package com.example.staffbe.strategy;

import com.example.staffbe.repository.PaymentRepository;
import com.example.staffbe.repository.RefundRepository;
import com.example.staffbe.repository.TutorApplicationRepository;
import com.example.staffbe.service.TutorApplicationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApprovalContextTest {

    @Mock
    private ApprovePaymentStrategy approvePaymentStrategy;

    @Mock
    private RejectPaymentStrategy rejectPaymentStrategy;

    @Mock
    private ApproveRefundStrategy approveRefundStrategy;

    @Mock
    private RejectRefundStrategy rejectRefundStrategy;

    @Mock
    private ApproveTutorApplicationStrategy approveTutorApplicationStrategy;

    @Mock
    private RejectTutorApplicationStrategy rejectTutorApplicationStrategy;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private RefundRepository refundRepository;

    @Mock
    private TutorApplicationRepository tutorApplicationRepository;

    @Mock
    private TutorApplicationServiceImpl tutorApplicationService;

    private ApprovalContext approvalContext;
    private UUID id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        approvalContext = new ApprovalContext(
            approveTutorApplicationStrategy,
            rejectTutorApplicationStrategy,
            approveRefundStrategy,
            rejectRefundStrategy,
            approvePaymentStrategy,
            rejectPaymentStrategy
        );
        id = UUID.randomUUID();
    }

    @Test
    void testApprovePayment() {
        // Act
        approvalContext.approve(id, "payment");

        // Assert
        verify(approvePaymentStrategy, times(1)).approve(id);
        verify(approveRefundStrategy, never()).approve(any());
        verify(approveTutorApplicationStrategy, never()).approve(any());
    }

    @Test
    void testApproveRefund() {
        // Act
        approvalContext.approve(id, "refund");

        // Assert
        verify(approveRefundStrategy, times(1)).approve(id);
        verify(approvePaymentStrategy, never()).approve(any());
        verify(approveTutorApplicationStrategy, never()).approve(any());
    }

    @Test
    void testApproveTutorApplication() {
        // Act
        approvalContext.approve(id, "tutor");

        // Assert
        verify(approveTutorApplicationStrategy, times(1)).approve(id);
        verify(approvePaymentStrategy, never()).approve(any());
        verify(approveRefundStrategy, never()).approve(any());
    }

    @Test
    void testRejectPayment() {
        // Act
        approvalContext.reject(id, "payment");

        // Assert
        verify(rejectPaymentStrategy, times(1)).reject(id);
        verify(rejectRefundStrategy, never()).reject(any());
        verify(rejectTutorApplicationStrategy, never()).reject(any());
    }

    @Test
    void testRejectRefund() {
        // Act
        approvalContext.reject(id, "refund");

        // Assert
        verify(rejectRefundStrategy, times(1)).reject(id);
        verify(rejectPaymentStrategy, never()).reject(any());
        verify(rejectTutorApplicationStrategy, never()).reject(any());
    }

    @Test
    void testRejectTutorApplication() {
        // Act
        approvalContext.reject(id, "tutor");

        // Assert
        verify(rejectTutorApplicationStrategy, times(1)).reject(id);
        verify(rejectPaymentStrategy, never()).reject(any());
        verify(rejectRefundStrategy, never()).reject(any());
    }

    @Test
    void testApproveInvalidType() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            approvalContext.approve(id, "invalid");
        });

        verify(approvePaymentStrategy, never()).approve(any());
        verify(approveRefundStrategy, never()).approve(any());
        verify(approveTutorApplicationStrategy, never()).approve(any());
    }

    @Test
    void testRejectInvalidType() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            approvalContext.reject(id, "invalid");
        });

        verify(rejectPaymentStrategy, never()).reject(any());
        verify(rejectRefundStrategy, never()).reject(any());
        verify(rejectTutorApplicationStrategy, never()).reject(any());
    }

    @Test
    void testApproveWithNullId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            approvalContext.approve(null, "payment");
        });

        verify(approvePaymentStrategy, never()).approve(any());
        verify(approveRefundStrategy, never()).approve(any());
        verify(approveTutorApplicationStrategy, never()).approve(any());
    }

    @Test
    void testRejectWithNullId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            approvalContext.reject(null, "payment");
        });

        verify(rejectPaymentStrategy, never()).reject(any());
        verify(rejectRefundStrategy, never()).reject(any());
        verify(rejectTutorApplicationStrategy, never()).reject(any());
    }

    @Test
    void testApproveWithNullType() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            approvalContext.approve(id, null);
        });

        verify(approvePaymentStrategy, never()).approve(any());
        verify(approveRefundStrategy, never()).approve(any());
        verify(approveTutorApplicationStrategy, never()).approve(any());
    }

    @Test
    void testRejectWithNullType() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            approvalContext.reject(id, null);
        });

        verify(rejectPaymentStrategy, never()).reject(any());
        verify(rejectRefundStrategy, never()).reject(any());
        verify(rejectTutorApplicationStrategy, never()).reject(any());
    }
}