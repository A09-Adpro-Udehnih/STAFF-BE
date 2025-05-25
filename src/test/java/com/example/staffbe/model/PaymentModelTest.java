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
        UUID userId = UUID.randomUUID(); // userId sekarang UUID
        double amount = 100.0;
        PaymentMethod method = PaymentMethod.BANK_TRANSFER;
        PaymentStatus status = PaymentStatus.PENDING;
        String paymentReference = "ref-123";
        LocalDateTime createdAt = LocalDateTime.now();

        Payment payment = Payment.builder()
                .id(paymentId)
                .userId(userId) // Menggunakan UUID
                .amount(amount)
                .method(method)
                .status(status)
                .paymentReference(paymentReference)
                .createdAt(createdAt)
                .build();

        assertEquals(paymentId, payment.getId());
        assertEquals(userId, payment.getUserId()); // Verifikasi UUID
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
        UUID userId = UUID.randomUUID(); // userId sekarang UUID
        double amount = 100.0;
        PaymentMethod method = PaymentMethod.BANK_TRANSFER;
        PaymentStatus status = PaymentStatus.PENDING;
        String paymentReference = "ref-123";
        LocalDateTime createdAt = LocalDateTime.now();

        payment.setId(paymentId);
        payment.setUserId(userId); // Menggunakan setUserId dengan UUID
        payment.setAmount(amount);
        payment.setMethod(method);
        payment.setStatus(status);
        payment.setPaymentReference(paymentReference);
        payment.setCreatedAt(createdAt);

        // Then
        assertEquals(paymentId, payment.getId());
        assertEquals(userId, payment.getUserId()); // Verifikasi UUID
        assertEquals(amount, payment.getAmount());
        assertEquals(method, payment.getMethod());
        assertEquals(status, payment.getStatus());
        assertEquals(paymentReference, payment.getPaymentReference());
        assertEquals(createdAt, payment.getCreatedAt());
    }

    @Test
    void testEquals() {
        UUID paymentId = UUID.randomUUID();
        UUID sharedUserId = UUID.randomUUID(); // UUID yang akan digunakan bersama untuk payment1 dan payment2
        UUID differentUserId = UUID.randomUUID(); // UUID yang berbeda untuk payment3

        Payment payment1 = Payment.builder()
                .id(paymentId)
                .userId(sharedUserId) // Menggunakan UUID
                .amount(100.0)
                .method(PaymentMethod.BANK_TRANSFER)
                .status(PaymentStatus.PENDING)
                .paymentReference("ref-common")
                .createdAt(LocalDateTime.now())
                .build();

        Payment payment2 = Payment.builder()
                .id(paymentId)
                .userId(sharedUserId) // Menggunakan UUID yang sama agar equals (jika userId bagian dari equals)
                .amount(100.0)
                .method(PaymentMethod.BANK_TRANSFER)
                .status(PaymentStatus.PENDING)
                .paymentReference("ref-common")
                .createdAt(payment1.getCreatedAt()) // Pastikan createdAt juga sama jika bagian dari equals
                .build();

        Payment payment3 = Payment.builder()
                .id(UUID.randomUUID()) // ID berbeda
                .userId(differentUserId) // UUID berbeda
                .amount(200.0)
                .method(PaymentMethod.CREDIT_CARD)
                .status(PaymentStatus.PAID)
                .paymentReference("ref-different")
                .createdAt(LocalDateTime.now().plusHours(1))
                .build();

        // Jika paymentReference dan createdAt juga bagian dari equals, pastikan mereka sama untuk payment1 & payment2
        // dan berbeda untuk payment3.
        // Untuk contoh ini, saya buat paymentReference dan createdAt sama untuk payment1 dan payment2.

        assertEquals(payment1, payment2, "Payment1 dan Payment2 seharusnya sama jika semua field relevan sama.");
        assertNotEquals(payment1, payment3, "Payment1 dan Payment3 seharusnya tidak sama.");
        assertNotEquals(payment2, payment3, "Payment2 dan Payment3 seharusnya tidak sama.");
    }
}