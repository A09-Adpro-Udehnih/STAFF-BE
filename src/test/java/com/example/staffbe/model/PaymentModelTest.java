package com.example.staffbe.model;

import com.example.staffbe.enums.PaymentMethod;
import com.example.staffbe.enums.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentModelTest {

    @Test
    void testPaymentBuilderAndGetters() {
        UUID paymentId = UUID.randomUUID();
        String userId = "user-123";
        double amount = 100.0;
        PaymentMethod method = PaymentMethod.BANK_TRANSFER;
        PaymentStatus status = PaymentStatus.PENDING;
        String paymentReference = "ref-123";
        LocalDateTime createdAt = LocalDateTime.now();

        Payment payment = Payment.builder()
                .id(paymentId)
                .userId(userId)
                .amount(amount)
                .method(method)
                .status(status)
                .paymentReference(paymentReference)
                .createdAt(createdAt)
                .build();

        assertEquals(paymentId, payment.getId());
        assertEquals(userId, payment.getUserId());
        assertEquals(amount, payment.getAmount());
        assertEquals(method, payment.getMethod());
        assertEquals(status, payment.getStatus());
        assertEquals(paymentReference, payment.getPaymentReference());
        assertEquals(createdAt, payment.getCreatedAt());
    }

    @Test
    void testPaymentSetters() {
        Payment payment = new Payment();
        UUID paymentId = UUID.randomUUID();
        String userId = "user-123";
        double amount = 100.0;
        PaymentMethod method = PaymentMethod.BANK_TRANSFER;
        PaymentStatus status = PaymentStatus.PENDING;
        String paymentReference = "ref-123";
        LocalDateTime createdAt = LocalDateTime.now();

        payment.setId(paymentId);
        payment.setUserId(userId);
        payment.setAmount(amount);
        payment.setMethod(method);
        payment.setStatus(status);
        payment.setPaymentReference(paymentReference);
        payment.setCreatedAt(createdAt);

        // Then
        assertEquals(paymentId, payment.getId());
        assertEquals(userId, payment.getUserId());
        assertEquals(amount, payment.getAmount());
        assertEquals(method, payment.getMethod());
        assertEquals(status, payment.getStatus());
        assertEquals(paymentReference, payment.getPaymentReference());
        assertEquals(createdAt, payment.getCreatedAt());
    }

    @Test
    void testEquals() {
        UUID paymentId = UUID.randomUUID();

        Payment payment1 = Payment.builder()
                .id(paymentId)
                .userId("user-123")
                .amount(100.0)
                .method(PaymentMethod.BANK_TRANSFER)
                .status(PaymentStatus.PENDING)
                .build();

        Payment payment2 = Payment.builder()
                .id(paymentId)
                .userId("user-123")
                .amount(100.0)
                .method(PaymentMethod.BANK_TRANSFER)
                .status(PaymentStatus.PENDING)
                .build();

        Payment payment3 = Payment.builder()
                .id(UUID.randomUUID())
                .userId("user-456")
                .amount(200.0)
                .method(PaymentMethod.CREDIT_CARD)
                .status(PaymentStatus.PAID)
                .build();

        assertEquals(payment1, payment2);
        assertNotEquals(payment1, payment3);
        assertNotEquals(payment2, payment3);
    }

}
