package com.example.staffbe.model;

import com.example.staffbe.enums.RefundStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RefundModelTest {

    @Test
    void testRefundCreationWithPrePersist() {
        Refund refund = Refund.builder()
                .id(UUID.randomUUID())
                .payment(mock(Payment.class))
                .amount(100)
                .reason("Testing default status")
                .build();

        refund.onCreate();

        assertNotNull(refund.getCreatedAt(), "createdAt harus otomatis terisi");
        assertEquals(RefundStatus.PENDING, refund.getStatus(), "Status default harus PENDING");
    }

    @Test
    void testRefundCreationWithExplicitStatus() {
        Refund refund = Refund.builder()
                .id(UUID.randomUUID())
                .payment(mock(Payment.class))
                .amount(150)
                .reason("Refund approved manually")
                .status(RefundStatus.ACCEPTED)
                .build();

        refund.onCreate();

        assertEquals(RefundStatus.ACCEPTED, refund.getStatus(), "Status tidak boleh diubah kalau sudah di-set");
    }
}
