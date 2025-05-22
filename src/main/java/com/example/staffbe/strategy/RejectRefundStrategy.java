package com.example.staffbe.strategy;

import com.example.staffbe.model.Refund;
import com.example.staffbe.repository.RefundRepository;
import com.example.staffbe.service.RefundServiceImpl;
import com.example.staffbe.enums.RefundStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RejectRefundStrategy implements ApprovalStrategy {

    private final RefundRepository refundRepository;
    private final RefundServiceImpl refundService;


    @Autowired
    public RejectRefundStrategy(RefundRepository refundRepository,RefundServiceImpl refundService) {
        this.refundRepository = refundRepository;
        this.refundService = refundService;
    }

    @Override
    public void approve(UUID refundId) {
        throw new UnsupportedOperationException("Approve operation is not supported for reject strategy");
    }

    @Override
    public void reject(UUID refundId) {
        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new RuntimeException("Refund not found"));

        refundService.rejectRefund(refundId); // BAGIAN INI HARUS DIUBAH MENJADI refund.approveRefund(refundId) UNTUK MENYESUAIKAN DENGAN REFUND SERVICE IMPL
        refundRepository.save(refund);  
    }
}
