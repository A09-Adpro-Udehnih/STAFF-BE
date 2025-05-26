package com.example.staffbe.strategy;

import com.example.staffbe.model.Refund;
import com.example.staffbe.repository.RefundRepository;


import org.springframework.stereotype.Component;

import com.example.staffbe.service.RefundServiceImpl;

import java.util.UUID;

@Component
public class ApproveRefundStrategy implements ApprovalStrategy {

    private final RefundRepository refundRepository;
    private final RefundServiceImpl refundService;

    
    public ApproveRefundStrategy(RefundRepository refundRepository, RefundServiceImpl refundService) {
        this.refundRepository = refundRepository;
        this.refundService = refundService;
    }

    @Override
    public void approve(UUID refundId) {
        if (refundId == null) {
            throw new IllegalArgumentException("Refund ID cannot be null");
        }

        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new RuntimeException("Refund not found"));

        refundService.approveRefund(refundId);
        refundRepository.save(refund);
    }
    

    @Override
    public void reject(UUID refundId) {
        throw new UnsupportedOperationException("Reject operation is not supported for approve strategy");
    }
}
