package com.example.staffbe.strategy;

import com.example.staffbe.model.Refund;
import com.example.staffbe.repository.RefundRepository;
import com.example.staffbe.enums.RefundStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RejectRefundStrategy implements ApprovalStrategy {

    private final RefundRepository refundRepository;

    @Autowired
    public RejectRefundStrategy(RefundRepository refundRepository) {
        this.refundRepository = refundRepository;
    }

    @Override
    public void approve(UUID refundId) {
        throw new UnsupportedOperationException("Approve operation is not supported for reject strategy");
    }

    @Override
    public void reject(UUID refundId) {
        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new RuntimeException("Refund not found"));

        refund.setStatus(RefundStatus.REJECTED);  // Menolak refund
        refundRepository.save(refund);  // Simpan status yang telah diperbarui
    }
}
