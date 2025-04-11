package com.example.staffbe.service;

import com.example.staffbe.model.RefundRequest;
import com.example.staffbe.model.RefundStatus;
import com.example.staffbe.repository.RefundRepository;
import com.example.staffbe.strategy.ApprovalStrategy;
import com.example.staffbe.strategy.RefundApprovalStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefundService {
    private final RefundRepository refundRepository;

    @Autowired
    public RefundService(RefundRepository refundRepository) {
        this.refundRepository = refundRepository;
    }

    public List<RefundRequest> getAllRefundRequests() {
        return refundRepository.findAll();
    }

    public List<RefundRequest> getPendingRefundRequests() {
        return refundRepository.findByStatus(RefundStatus.PENDING.toString());
    }

    public Optional<RefundRequest> getRefundRequestById(UUID refundId) {
        return refundRepository.findById(refundId);
    }

    public void approveRefund(UUID refundId) {
        refundRepository.findById(refundId).ifPresent(refund -> {
            ApprovalStrategy strategy = new RefundApprovalStrategy(refund);
            strategy.approve();
            refundRepository.save(refund);
        });
    }

    public void rejectRefund(UUID refundId) {
        refundRepository.findById(refundId).ifPresent(refund -> {
            ApprovalStrategy strategy = new RefundApprovalStrategy(refund);
            strategy.reject();
            refundRepository.save(refund);
        });
    }
}
