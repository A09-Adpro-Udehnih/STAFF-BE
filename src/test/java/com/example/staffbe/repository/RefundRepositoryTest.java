package com.example.staffbe.repository;

import com.example.staffbe.enums.PaymentMethod;
import com.example.staffbe.enums.PaymentStatus;
import com.example.staffbe.enums.RefundStatus;
import com.example.staffbe.model.Payment;
import com.example.staffbe.model.Refund;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RefundRepositoryTest {
    UUID randomId = UUID.randomUUID();

    @Autowired
    private RefundRepository refundRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    private Payment createAndSavePayment() {
        Payment payment = Payment.builder()
                .userId(randomId) // Gunakan UUID untuk userId
                .amount(250.00)
                .method(PaymentMethod.BANK_TRANSFER)
                .status(PaymentStatus.PAID)
                .createdAt(LocalDateTime.now())
                .build();
        return paymentRepository.save(payment);
    }

    @Test
    @DisplayName("Should save and retrieve Refund by ID")
    void testSaveAndFindById() {
        Payment payment = createAndSavePayment();
        UUID refundId = UUID.randomUUID();

        Refund refund = Refund.builder()
                .id(refundId)
                .payment(payment)
                .amount(250)
                .status(RefundStatus.ACCEPTED)
                .reason("Test case")
                .createdAt(LocalDateTime.now())
                .build();

        refundRepository.save(refund);

        Optional<Refund> found = refundRepository.findById(refundId);
        assertTrue(found.isPresent());
        assertEquals(RefundStatus.ACCEPTED, found.get().getStatus());
        assertEquals(randomId, found.get().getPayment().getUserId());
    }

    @Test
    @DisplayName("Should retrieve all Refunds")
    void testFindAll() {
        Payment payment = createAndSavePayment();

        Refund r1 = Refund.builder()
                .id(UUID.randomUUID())
                .payment(payment)
                .amount(100)
                .status(RefundStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        Refund r2 = Refund.builder()
                .id(UUID.randomUUID())
                .payment(payment)
                .amount(200)
                .status(RefundStatus.REJECTED)
                .createdAt(LocalDateTime.now())
                .build();

        refundRepository.save(r1);
        refundRepository.save(r2);

        List<Refund> refunds = refundRepository.findAll();
        assertEquals(2, refunds.size());
    }
}
