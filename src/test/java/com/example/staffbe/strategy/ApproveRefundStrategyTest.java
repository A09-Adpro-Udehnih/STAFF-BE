package com.example.staffbe.strategy;

import com.example.staffbe.model.Refund;
import com.example.staffbe.repository.RefundRepository;
import com.example.staffbe.service.RefundServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApproveRefundStrategyTest {

    @Mock
    private RefundRepository refundRepository;

    @Mock
    private RefundServiceImpl refundService;

    private ApproveRefundStrategy approveRefundStrategy;
    private UUID refundId;
    private Refund mockRefund;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        approveRefundStrategy = new ApproveRefundStrategy(refundRepository, refundService);
        refundId = UUID.randomUUID();
        mockRefund = new Refund();
        mockRefund.setId(refundId);
    }

    @Test
    void testApproveRefund() {
        // Arrange
        when(refundRepository.findById(refundId)).thenReturn(Optional.of(mockRefund));
        when(refundRepository.save(any(Refund.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        approveRefundStrategy.approve(refundId);

        // Assert
        verify(refundRepository, times(1)).findById(refundId);
        verify(refundService, times(1)).approveRefund(refundId);
        verify(refundRepository, times(1)).save(any(Refund.class));
    }

    @Test
    void testApproveRefundNotFound() {
        // Arrange
        when(refundRepository.findById(refundId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            approveRefundStrategy.approve(refundId);
        });

        verify(refundRepository, times(1)).findById(refundId);
        verify(refundService, never()).approveRefund(any());
        verify(refundRepository, never()).save(any(Refund.class));
    }

    @Test
    void testApproveRefundWithNullId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            approveRefundStrategy.approve(null);
        });

        verify(refundRepository, never()).findById(any());
        verify(refundService, never()).approveRefund(any());
        verify(refundRepository, never()).save(any(Refund.class));
    }

    @Test
    void testRejectNotSupported() {
        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> {
            approveRefundStrategy.reject(refundId);
        });

        verify(refundRepository, never()).findById(any());
        verify(refundService, never()).approveRefund(any());
        verify(refundRepository, never()).save(any(Refund.class));
    }
}
