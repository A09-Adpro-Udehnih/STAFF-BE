package com.example.staffbe.strategy;

import com.example.staffbe.model.Payment;
import com.example.staffbe.repository.PaymentRepository;
import com.example.staffbe.enums.PaymentStatus;

import org.springframework.stereotype.Component;

import com.example.staffbe.service.PaymentServiceImpl;

import java.util.UUID;

@Component
public class RejectPaymentStrategy implements ApprovalStrategy {

    private final PaymentRepository paymentRepository;
    private final PaymentServiceImpl paymentService;

    
    public RejectPaymentStrategy(PaymentRepository paymentRepository, PaymentServiceImpl paymentService) {
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
    }

    @Override
    public void reject(UUID paymentId) {
        if (paymentId == null) {
            throw new IllegalArgumentException("Payment ID cannot be null");
        }

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        paymentService.updatePaymentStatus(paymentId, PaymentStatus.FAILED); 
        paymentRepository.save(payment);  
    }
    

    @Override
    public void approve(UUID paymentId) {
        throw new UnsupportedOperationException("Reject operation is not supported for approve strategy");
    }
}
