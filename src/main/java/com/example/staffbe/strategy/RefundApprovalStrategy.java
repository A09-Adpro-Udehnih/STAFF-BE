package com.example.staffbe.strategy;

import com.example.staffbe.model.RefundRequest;
import com.example.staffbe.model.RefundStatus;

public class RefundApprovalStrategy implements ApprovalStrategy {
    private final RefundRequest request;

    public RefundApprovalStrategy(RefundRequest request) {
        this.request = request;
    }

    @Override
    public void approve() {
        request.setStatus(RefundStatus.ACCEPTED);
    }

    @Override
    public void reject() {
        request.setStatus(RefundStatus.REJECTED);
    }
}
