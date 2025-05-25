package com.example.staffbe.service;

import com.example.staffbe.enums.PaymentMethod;
import com.example.staffbe.enums.PaymentStatus;
import com.example.staffbe.model.Payment;
import com.example.staffbe.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID; // Pastikan UUID diimpor

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService; // Asumsi nama implementasi service Anda adalah PaymentServiceImpl

    private Payment testPayment;
    private UUID testUserId; // Variabel untuk menyimpan UUID userId

    @BeforeEach
    void setUp() {
        testUserId = UUID.randomUUID(); // Inisialisasi testUserId sebagai UUID
        testPayment = Payment.builder()
                .id(UUID.randomUUID())
                .userId(testUserId) // <-- UBAH DI SINI: Gunakan UUID
                .amount(100.0)
                .method(PaymentMethod.BANK_TRANSFER)
                .status(PaymentStatus.PENDING)
                .paymentReference("ref-123")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testGetAllPayments() {
        when(paymentRepository.findAll()).thenReturn(List.of(testPayment));

        List<Payment> payments = paymentService.getAllPayments();

        assertNotNull(payments);
        assertFalse(payments.isEmpty());
        assertEquals(1, payments.size());
        assertEquals(testPayment, payments.get(0));
        // Verifikasi bahwa userId di payment yang dikembalikan adalah UUID yang sama
        assertEquals(testUserId, payments.get(0).getUserId());
    }

    @Test
    void testUpdatePaymentStatus_Success() {
        when(paymentRepository.findById(testPayment.getId())).thenReturn(Optional.of(testPayment));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Method yang diuji
        paymentService.updatePaymentStatus(testPayment.getId(), PaymentStatus.PAID);

        // Verifikasi efek samping
        assertEquals(PaymentStatus.PAID, testPayment.getStatus(), "Status pembayaran seharusnya sudah PAID");
        verify(paymentRepository).findById(testPayment.getId());
        verify(paymentRepository).save(testPayment);
    }

    @Test
    void testUpdatePaymentStatus_NotFound() {
        UUID invalidId = UUID.randomUUID();
        when(paymentRepository.findById(invalidId)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> paymentService.updatePaymentStatus(invalidId, PaymentStatus.PAID)
        );

        // Sesuaikan pesan error jika berbeda di implementasi service Anda
        // Misalnya, jika service Anda melempar IllegalArgumentException atau Exception spesifik lainnya
        assertTrue(thrown.getMessage().contains("Payment not found") || thrown.getMessage().contains(invalidId.toString()),
                "Pesan error seharusnya mengindikasikan payment tidak ditemukan");
        verify(paymentRepository).findById(invalidId); // Verifikasi findById dipanggil
        verify(paymentRepository, never()).save(any(Payment.class)); // Pastikan save tidak pernah dipanggil
    }
}