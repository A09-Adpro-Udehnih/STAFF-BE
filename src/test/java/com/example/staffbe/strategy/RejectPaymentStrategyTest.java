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
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.UUID;

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
        mockPayment = Payment.builder()
                .id(paymentId)
                .amount(100.0)
                .status(PaymentStatus.PENDING)  // Status sebelum reject
                .build();
    }

    @Test
    void testRejectPayment() {
        // Simulasi behavior repository untuk menemukan payment
        when(paymentRepository.findById(paymentId)).thenReturn(java.util.Optional.of(mockPayment));

        // Simulasi behavior untuk mengubah status payment menjadi REJECTED
        doAnswer(invocation -> {
            mockPayment.setStatus(PaymentStatus.FAILED);  // Mengubah status payment menjadi REJECTED
            return null;
        }).when(paymentRepository).save(mockPayment);  // Simulasi pemanggilan save() pada repository

        // Panggil reject untuk payment
        rejectPaymentStrategy.reject(paymentId);

        // Verifikasi bahwa status payment terupdate menjadi REJECTED
        assertEquals(PaymentStatus.FAILED, mockPayment.getStatus());  // Pastikan statusnya terupdate menjadi REJECTED

        // Verifikasi bahwa rejectPayment dipanggil dengan ID yang benar
        verify(paymentService, times(1)).updatePaymentStatus(paymentId, PaymentStatus.FAILED);

        // Verifikasi bahwa save() dipanggil pada repository untuk menyimpan perubahan status
        verify(paymentRepository, times(1)).save(mockPayment);  // Memastikan bahwa save dipanggil sekali
    }
}
