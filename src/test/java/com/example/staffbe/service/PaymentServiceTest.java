package com.example.staffbe.service;

import com.example.staffbe.model.Payment;
import com.example.staffbe.enums.PaymentMethod;
import com.example.staffbe.enums.PaymentStatus;
import com.example.staffbe.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Payment testPayment;

    @BeforeEach
    void setUp() {
        testPayment = Payment.builder()
                .id(UUID.randomUUID())
                .userId("user-123")
                .amount(100.0)
                .method(PaymentMethod.BANK_TRANSFER)
                .status(PaymentStatus.PENDING)
                .paymentReference("ref-123")
                .createdAt(java.time.LocalDateTime.now())
                .build();
    }

    @Test
    void testGetAllPayments() {
        // Simulasi pengembalian data pembayaran dari repository
        when(paymentRepository.findAll()).thenReturn(List.of(testPayment));

        List<Payment> payments = paymentService.getAllPayments();

        assertNotNull(payments);
        assertFalse(payments.isEmpty());
        assertEquals(1, payments.size());
        assertEquals(testPayment, payments.get(0));
    }

    @Test
    void testUpdatePaymentStatus_Success() {
        // Simulasi pengembalian data pembayaran berdasarkan ID
        when(paymentRepository.findById(testPayment.getId())).thenReturn(Optional.of(testPayment));

        int rowsUpdated = paymentService.updatePaymentStatus(testPayment.getId(), PaymentStatus.PAID);

        // Verifikasi jika status berhasil diperbarui
        assertEquals(1, rowsUpdated);  // Pastikan 1 baris terpengaruh
        assertEquals(PaymentStatus.PAID, testPayment.getStatus());  // Pastikan status berubah
    }

    @Test
    void testUpdatePaymentStatus_NotFound() {
        // Simulasi ketika pembayaran tidak ditemukan di repository
        when(paymentRepository.findById(testPayment.getId())).thenReturn(Optional.empty());

        int rowsUpdated = paymentService.updatePaymentStatus(testPayment.getId(), PaymentStatus.PAID);

        // Verifikasi bahwa status tidak diperbarui karena pembayaran tidak ditemukan
        assertEquals(0, rowsUpdated);  // 0 berarti pembayaran tidak ditemukan
    }
}
