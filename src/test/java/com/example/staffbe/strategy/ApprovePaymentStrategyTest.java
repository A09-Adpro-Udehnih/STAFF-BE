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

public class ApprovePaymentStrategyTest {

    @Mock
    private PaymentRepository paymentRepository;  // Mocking PaymentRepository

    @Mock
    private PaymentServiceImpl paymentService;  // Mocking PaymentServiceImpl

    @InjectMocks
    private ApprovePaymentStrategy approvePaymentStrategy;  // Injecting mocks into ApprovePaymentStrategy

    private UUID paymentId;
    private Payment mockPayment;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        paymentId = UUID.randomUUID();  // Simulasi ID payment yang akan diapprove
        mockPayment = Payment.builder()
                .id(paymentId)
                .amount(100.0)
                .status(PaymentStatus.PENDING)  // Status sebelum approve
                .build();
    }

    @Test
    void testApprovePayment() {
        // Simulasi behavior repository untuk menemukan payment
        when(paymentRepository.findById(paymentId)).thenReturn(java.util.Optional.of(mockPayment));

        // Simulasi behavior untuk mengubah status payment ketika approve dipanggil
        doAnswer(invocation -> {
            mockPayment.setStatus(PaymentStatus.PAID);  // Mengubah status payment menjadi PAID
            return null;
        }).when(paymentRepository).save(mockPayment);  // Simulasi pemanggilan save() pada repository

        // Panggil approve untuk payment
        approvePaymentStrategy.approve(paymentId);

        // Verifikasi bahwa status payment terupdate menjadi PAID
        assertEquals(PaymentStatus.PAID, mockPayment.getStatus());  // Pastikan statusnya terupdate menjadi PAID

        // Verifikasi bahwa approvePayment dipanggil dengan ID yang benar
        verify(paymentService, times(1)).updatePaymentStatus(paymentId, PaymentStatus.PAID);

        // Verifikasi bahwa save() dipanggil pada repository untuk menyimpan perubahan status
        verify(paymentRepository, times(1)).save(mockPayment);  // Memastikan bahwa save dipanggil sekali
    }
}
