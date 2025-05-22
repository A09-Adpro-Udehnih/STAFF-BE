package com.example.staffbe.repository;

import com.example.staffbe.model.Payment;
import com.example.staffbe.enums.PaymentMethod;
import com.example.staffbe.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    private Payment testPayment;

    @BeforeEach
    void setUp() {
        testPayment = Payment.builder()
                .userId("user-123")
                .amount(100.0)
                .method(PaymentMethod.BANK_TRANSFER)
                .status(PaymentStatus.PENDING)
                .paymentReference("ref-123")
                .createdAt(java.time.LocalDateTime.now())
                .build();

        paymentRepository.save(testPayment);
    }

    @Test
    void testFindById() {
        // Cari pembayaran berdasarkan ID
        Optional<Payment> retrievedPayment = paymentRepository.findById(testPayment.getId());

        assertTrue(retrievedPayment.isPresent()); // Pastikan pembayaran ditemukan
        assertEquals(testPayment.getId(), retrievedPayment.get().getId());
        assertEquals(testPayment.getUserId(), retrievedPayment.get().getUserId());
    }

    @Test
    void testFindById_NotFound() {
        // Cari pembayaran dengan ID yang tidak ada
        UUID randomId = UUID.randomUUID();
        Optional<Payment> retrievedPayment = paymentRepository.findById(randomId);

        assertFalse(retrievedPayment.isPresent()); // Pastikan pembayaran tidak ditemukan
    }

    @Test
    void testFindAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        assertNotNull(payments);
        assertFalse(payments.isEmpty());
        assertEquals(1, payments.size());  // Assuming only 1 payment is saved
    }
}