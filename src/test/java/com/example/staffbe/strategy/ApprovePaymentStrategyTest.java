package com.example.staffbe.strategy;

import com.example.staffbe.model.Payment;
import com.example.staffbe.repository.PaymentRepository;
import com.example.staffbe.enums.PaymentStatus;
import com.example.staffbe.service.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApprovePaymentStrategyTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentServiceImpl paymentService;

    private ApprovePaymentStrategy approvePaymentStrategy;
    private UUID paymentId;
    private Payment mockPayment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        approvePaymentStrategy = new ApprovePaymentStrategy(paymentRepository, paymentService);
        paymentId = UUID.randomUUID();
        mockPayment = new Payment();
        mockPayment.setId(paymentId);
        mockPayment.setStatus(PaymentStatus.PENDING);
    }

    @Test
    void testApprovePayment() {
        // Arrange
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(mockPayment));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        approvePaymentStrategy.approve(paymentId);

        // Assert
        verify(paymentRepository, times(1)).findById(paymentId);
        verify(paymentService, times(1)).updatePaymentStatus(paymentId, PaymentStatus.PAID);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testApprovePaymentNotFound() {
        // Arrange
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            approvePaymentStrategy.approve(paymentId);
        });

        verify(paymentRepository, times(1)).findById(paymentId);
        verify(paymentService, never()).updatePaymentStatus(any(), any());
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void testApprovePaymentWithNullId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            approvePaymentStrategy.approve(null);
        });

        verify(paymentRepository, never()).findById(any());
        verify(paymentService, never()).updatePaymentStatus(any(), any());
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void testRejectNotSupported() {
        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> {
            approvePaymentStrategy.reject(paymentId);
        });

        verify(paymentRepository, never()).findById(any());
        verify(paymentService, never()).updatePaymentStatus(any(), any());
        verify(paymentRepository, never()).save(any(Payment.class));
    }
}
