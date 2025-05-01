package com.example.staffbe.model;

import com.example.staffbe.enums.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RefundModelTest {

    @Test
    void testRefundCreationWithPrePersist() {
        // Arrange
        Refund refund = Refund.builder()
                .id(UUID.randomUUID())
                .payment(mock(Payment.class))
                .amount(new BigDecimal("100.00"))
                .reason("Testing")
                .build();

        // Act
        refund.onCreate();  // Simulasi PrePersist lifecycle event

        // Assert
        assertNotNull(refund.getCreatedAt(), "createdAt should be initialized");
        assertEquals(PaymentStatus.PENDING, refund.getStatus(), "status should be set to PENDING by default");
    }

    @Test
    void testRefundCreationWithExplicitStatus() {
        // Arrange
        Refund refund = Refund.builder()
                .id(UUID.randomUUID())
                .payment(mock(Payment.class))
                .amount(new BigDecimal("150.00"))
                .reason("Refund for overcharge")
                .status(PaymentStatus.REFUNDED)
                .build();

        // Act
        refund.onCreate();  // Even if status already set

        // Assert
        assertEquals(PaymentStatus.REFUNDED, refund.getStatus(), "status should remain as REFUNDED");
    }
}
