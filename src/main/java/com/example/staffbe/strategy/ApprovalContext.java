package com.example.staffbe.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.staffbe.service.*;



import java.util.UUID;

@Component
public class ApprovalContext {

    private final ApprovalStrategy approveTutorApplicationStrategy;
    private final ApprovalStrategy rejectTutorApplicationStrategy;
    private final ApprovalStrategy approveRefundStrategy;
    private final ApprovalStrategy rejectRefundStrategy;
    private final ApprovalStrategy approvePaymentStrategy;
    private final ApprovalStrategy rejectPaymentStrategy;

    @Autowired
    public ApprovalContext(
            ApprovalStrategy approveTutorApplicationStrategy,
            ApprovalStrategy rejectTutorApplicationStrategy,
            ApprovalStrategy approveRefundStrategy,
            ApprovalStrategy rejectRefundStrategy,
            ApprovalStrategy approvePaymentStrategy,
            ApprovalStrategy rejectPaymentStrategy) {
        this.approveTutorApplicationStrategy = approveTutorApplicationStrategy;
        this.rejectTutorApplicationStrategy = rejectTutorApplicationStrategy;
        this.approveRefundStrategy = approveRefundStrategy;
        this.rejectRefundStrategy = rejectRefundStrategy;
        this.approvePaymentStrategy = approvePaymentStrategy;
        this.rejectPaymentStrategy = rejectPaymentStrategy;
    }

    // Method untuk approve, memilih strategi berdasarkan tipe
    public void approve(UUID id, String type) {
        switch (type) {
            case "tutorApplication":
                approveTutorApplicationStrategy.approve(id);
                break;
            case "refund":
                approveRefundStrategy.approve(id);
                break;
            case "payment":
                approvePaymentStrategy.approve(id);
                break;
            default:
                throw new IllegalArgumentException("Invalid type");
        }
    }

    // Method untuk reject, memilih strategi berdasarkan tipe
    public void reject(UUID id, String type) {
        switch (type) {
            case "tutorApplication":
                rejectTutorApplicationStrategy.reject(id);
                break;
            case "refund":
                rejectRefundStrategy.reject(id);
                break;
            case "payment":
                rejectPaymentStrategy.reject(id);
                break;
            default:
                throw new IllegalArgumentException("Invalid type");
        }
    }
}
