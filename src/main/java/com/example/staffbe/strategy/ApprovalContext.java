package com.example.staffbe.strategy;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ApprovalContext {

    private final ApprovalStrategy approveTutorApplicationStrategy;
    private final ApprovalStrategy rejectTutorApplicationStrategy;
    private final ApprovalStrategy approveRefundStrategy;
    private final ApprovalStrategy rejectRefundStrategy;
    private final ApprovalStrategy approvePaymentStrategy;
    private final ApprovalStrategy rejectPaymentStrategy;

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

    public void approve(UUID id, String type) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }

        switch (type.toLowerCase()) {
            case "tutor":
            case "tutorapplication":
                approveTutorApplicationStrategy.approve(id);
                break;
            case "refund":
                approveRefundStrategy.approve(id);
                break;
            case "payment":
                approvePaymentStrategy.approve(id);
                break;
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
    }

    public void reject(UUID id, String type) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }

        switch (type.toLowerCase()) {
            case "tutor":
            case "tutorapplication":
                rejectTutorApplicationStrategy.reject(id);
                break;
            case "refund":
                rejectRefundStrategy.reject(id);
                break;
            case "payment":
                rejectPaymentStrategy.reject(id);
                break;
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
    }
}
