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

class RejectRefundStrategyTest {

    @Mock
    private RefundRepository refundRepository;

    @Mock
    private RefundServiceImpl refundService;

    private RejectRefundStrategy rejectRefundStrategy;
    private UUID refundId;
    private Refund mockRefund;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rejectRefundStrategy = new RejectRefundStrategy(refundRepository, refundService);
        refundId = UUID.randomUUID();
        mockRefund = new Refund();
        mockRefund.setId(refundId);
    }

    @Test
    void testRejectRefund() {
        // Arrange
        when(refundRepository.findById(refundId)).thenReturn(Optional.of(mockRefund));
        when(refundRepository.save(any(Refund.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        rejectRefundStrategy.reject(refundId);

        // Assert
        verify(refundRepository, times(1)).findById(refundId);
        verify(refundService, times(1)).rejectRefund(refundId);
        verify(refundRepository, times(1)).save(any(Refund.class));
    }

    @Test
    void testRejectRefundNotFound() {
        // Arrange
        when(refundRepository.findById(refundId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            rejectRefundStrategy.reject(refundId);
        });

        verify(refundRepository, times(1)).findById(refundId);
        verify(refundService, never()).rejectRefund(any());
        verify(refundRepository, never()).save(any(Refund.class));
    }

    @Test
    void testRejectRefundWithNullId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            rejectRefundStrategy.reject(null);
        });

        verify(refundRepository, never()).findById(any());
        verify(refundService, never()).rejectRefund(any());
        verify(refundRepository, never()).save(any(Refund.class));
    }

    @Test
    void testApproveNotSupported() {
        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> {
            rejectRefundStrategy.approve(refundId);
        });

        verify(refundRepository, never()).findById(any());
        verify(refundService, never()).rejectRefund(any());
        verify(refundRepository, never()).save(any(Refund.class));
    }
}
