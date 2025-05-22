package com.example.staffbe.strategy;

import com.example.staffbe.model.Refund;
import com.example.staffbe.repository.RefundRepository;
import com.example.staffbe.enums.RefundStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.staffbe.service.RefundServiceImpl;

import java.util.UUID;

import ch.qos.logback.core.net.SyslogOutputStream;

@Component
public class ApproveRefundStrategy implements ApprovalStrategy {

    private final RefundRepository refundRepository;
    private final RefundServiceImpl refundService;

    @Autowired
    public ApproveRefundStrategy(RefundRepository refundRepository, RefundServiceImpl refundService) {
        this.refundRepository = refundRepository;
        this.refundService = refundService;
    }

    @Override
    public void approve(UUID refundId) {
        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new RuntimeException("Refund not found"));

        refundService.approveRefund(refundId); // BAGIAN INI HARUS DIUBAH MENJADI refund.approveRefund(refundId) UNTUK MENYESUAIKAN DENGAN REFUND SERVICE IMPL
        refundRepository.save(refund);  
    }
    

    @Override
    public void reject(UUID refundId) {
        throw new UnsupportedOperationException("Reject operation is not supported for approve strategy");
    }
}
