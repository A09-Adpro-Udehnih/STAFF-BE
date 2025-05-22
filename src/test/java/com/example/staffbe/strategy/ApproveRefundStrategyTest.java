package com.example.staffbe.strategy;

import com.example.staffbe.model.Refund;
import com.example.staffbe.repository.RefundRepository;
import com.example.staffbe.service.RefundServiceImpl;
import com.example.staffbe.enums.RefundStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.UUID;

public class ApproveRefundStrategyTest {

    @Mock
    private RefundRepository refundRepository;  // Mocking RefundRepository

    @Mock
    private RefundServiceImpl refundService;  // Mocking RefundServiceImpl

    @InjectMocks
    private ApproveRefundStrategy approveRefundStrategy;  // Injecting mocks into ApproveRefundStrategy

    private UUID refundId;
    private Refund mockRefund;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        refundId = UUID.randomUUID();  // Simulasi ID refund yang akan diapprove
        mockRefund = Refund.builder()
                .id(refundId)
                .amount(50.0)
                .status(RefundStatus.PENDING)  // Status sebelum approve
                .build();
    }

    @Test
    void testApproveRefund() {
        // Simulasi behavior repository untuk menemukan refund
        when(refundRepository.findById(refundId)).thenReturn(java.util.Optional.of(mockRefund));

        // Simulasi behavior untuk mengubah status refund ketika approve dipanggil
        doAnswer(invocation -> {
            mockRefund.setStatus(RefundStatus.ACCEPTED);  // Mengubah status refund menjadi APPROVED
            return null;
        }).when(refundRepository).save(mockRefund);  // Simulasi pemanggilan save() pada repository

        // Panggil approve untuk refund
        approveRefundStrategy.approve(refundId);

        // Verifikasi bahwa status refund terupdate menjadi APPROVED
        assertEquals(RefundStatus.ACCEPTED, mockRefund.getStatus());  // Memastikan status terupdate menjadi APPROVED

        // Verifikasi bahwa refundService.approveRefund dipanggil dengan ID yang benar
        verify(refundService, times(1)).approveRefund(refundId);

        // Verifikasi bahwa save() dipanggil pada repository untuk menyimpan perubahan status
        verify(refundRepository, times(1)).save(mockRefund);  // Memastikan bahwa save dipanggil sekali
    }
}
