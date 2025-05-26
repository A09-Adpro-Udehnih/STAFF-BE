package com.example.staffbe.strategy;

import com.example.staffbe.model.Payment;
import com.example.staffbe.repository.PaymentRepository;
import com.example.staffbe.service.PaymentServiceImpl;
import com.example.staffbe.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import java.util.Optional;

public class RejectPaymentStrategyTest {

    @Mock
    private PaymentRepository paymentRepository;  // Mocking PaymentRepository

    @Mock
    private PaymentServiceImpl paymentService;  // Mocking PaymentServiceImpl

    @InjectMocks
    private RejectPaymentStrategy rejectPaymentStrategy;  // Injecting mocks into RejectPaymentStrategy

    private UUID paymentId;
    private Payment mockPayment;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        paymentId = UUID.randomUUID();  // Simulasi ID payment yang akan direject
        mockPayment = new Payment();
        mockPayment.setId(paymentId);
        mockPayment.setStatus(PaymentStatus.PENDING);  // Status sebelum reject
    }

    @Test
    void testRejectPayment() {
        // Arrange
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(mockPayment));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        rejectPaymentStrategy.reject(paymentId);

        // Assert
        verify(paymentRepository, times(1)).findById(paymentId);
        verify(paymentService, times(1)).updatePaymentStatus(paymentId, PaymentStatus.FAILED);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testRejectPaymentNotFound() {
        // Arrange
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            rejectPaymentStrategy.reject(paymentId);
        });

        verify(paymentRepository, times(1)).findById(paymentId);
        verify(paymentService, never()).updatePaymentStatus(any(), any());
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void testRejectPaymentWithNullId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            rejectPaymentStrategy.reject(null);
        });

        verify(paymentRepository, never()).findById(any());
        verify(paymentService, never()).updatePaymentStatus(any(), any());
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void testApproveNotSupported() {
        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> {
            rejectPaymentStrategy.approve(paymentId);
        });

        verify(paymentRepository, never()).findById(any());
        verify(paymentService, never()).updatePaymentStatus(any(), any());
        verify(paymentRepository, never()).save(any(Payment.class));
    }
}
