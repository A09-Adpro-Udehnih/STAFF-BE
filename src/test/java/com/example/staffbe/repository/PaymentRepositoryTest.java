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
        UUID userIdAsUUID = UUID.randomUUID();

        testPayment = Payment.builder()
                .userId(userIdAsUUID)
                .amount(100.0)
                .method(PaymentMethod.BANK_TRANSFER)
                .status(PaymentStatus.PENDING)
                .paymentReference("ref-123")
                .createdAt(java.time.LocalDateTime.now())
                .build();

        testPayment = paymentRepository.save(testPayment);
    }

    @Test
    void testFindById() {
        Optional<Payment> retrievedPayment = paymentRepository.findById(testPayment.getId());

        assertTrue(retrievedPayment.isPresent(), "Pembayaran seharusnya ditemukan");
        assertEquals(testPayment.getId(), retrievedPayment.get().getId());
        assertEquals(testPayment.getUserId(), retrievedPayment.get().getUserId(), "User ID seharusnya cocok");
    }

    @Test
    void testFindById_NotFound() {
        UUID randomId = UUID.randomUUID();
        Optional<Payment> retrievedPayment = paymentRepository.findById(randomId);

        assertFalse(retrievedPayment.isPresent(), "Pembayaran seharusnya tidak ditemukan untuk ID acak");
    }

    @Test
    void testFindAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        assertNotNull(payments, "Daftar pembayaran tidak boleh null");
        assertFalse(payments.isEmpty(), "Daftar pembayaran tidak boleh kosong setelah setUp");
        assertEquals(1, payments.size(), "Seharusnya hanya ada satu pembayaran dari setUp");
        assertEquals(testPayment.getId(), payments.get(0).getId(), "ID pembayaran di daftar seharusnya cocok");
    }
}