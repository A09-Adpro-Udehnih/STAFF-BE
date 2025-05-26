package com.example.staffbe.service;

import com.example.staffbe.model.Refund;
import com.example.staffbe.model.Payment;
import com.example.staffbe.enums.RefundStatus;
import com.example.staffbe.repository.RefundRepository;
import com.example.staffbe.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.staffbe.enums.PaymentMethod;
import com.example.staffbe.enums.PaymentStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RefundServiceTest {

    @Mock
    private RefundRepository refundRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private RefundServiceImpl refundService;

    private Refund testRefund;
    private Payment testPayment;
    private UUID refundId;

    @BeforeEach
    void setUp() {
        UUID paymentId = UUID.randomUUID();
        UUID testPaymentUserId = UUID.randomUUID();

        testPayment = Payment.builder()
                .id(paymentId)
                .userId(testPaymentUserId)
                .amount(100.0)
                .status(PaymentStatus.PENDING)
                .method(PaymentMethod.BANK_TRANSFER)
                .paymentReference("ref-123")
                .createdAt(java.time.LocalDateTime.now())
                .build();

        refundId = UUID.randomUUID();
        testRefund = Refund.builder()
                .id(refundId)
                .payment(testPayment)
                .amount(50.0)
                .status(RefundStatus.PENDING)
                .reason("Product refund")
                .createdAt(java.time.LocalDateTime.now())
                .build();
    }

    @Test
    void testApproveRefund() {
        when(refundRepository.findById(refundId)).thenReturn(Optional.of(testRefund));
        when(paymentRepository.save(testPayment)).thenReturn(testPayment);

        refundService.approveRefund(refundId);

        assertEquals(RefundStatus.ACCEPTED, testRefund.getStatus());
        assertEquals(PaymentStatus.PAID, testPayment.getStatus());
        verify(refundRepository).save(testRefund);
    }

    @Test
    void testApproveRefund_NotFound() {
        when(refundRepository.findById(refundId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            refundService.approveRefund(refundId);
        });

        verify(refundRepository, never()).save(any());
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void testRejectRefund() {
        when(refundRepository.findById(refundId)).thenReturn(Optional.of(testRefund));

        refundService.rejectRefund(refundId);

        assertEquals(RefundStatus.REJECTED, testRefund.getStatus());
        verify(refundRepository).save(testRefund);
    }

    @Test
    void testRejectRefund_NotFound() {
        when(refundRepository.findById(refundId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            refundService.rejectRefund(refundId);
        });

        verify(refundRepository, never()).save(any());
    }

    @Test
    void testFindRefundById() {
        when(refundRepository.findById(refundId)).thenReturn(Optional.of(testRefund));

        Refund refund = refundService.findRefundById(refundId);

        assertNotNull(refund);
        assertEquals(refundId, refund.getId());
        assertEquals("Product refund", refund.getReason());
    }

    @Test
    void testFindRefundById_NotFound() {
        when(refundRepository.findById(refundId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            refundService.findRefundById(refundId);
        });
    }

    @Test
    void testFindAllRefunds() {
        when(refundRepository.findAll()).thenReturn(List.of(testRefund));

        List<Refund> refunds = refundService.findAllRefunds();

        assertNotNull(refunds);
        assertFalse(refunds.isEmpty());
        assertEquals(testRefund, refunds.get(0));
    }

    @Test
    void testFindAllRefunds_Empty() {
        when(refundRepository.findAll()).thenReturn(List.of());

        List<Refund> refunds = refundService.findAllRefunds();

        assertNotNull(refunds);
        assertTrue(refunds.isEmpty());
    }
}
