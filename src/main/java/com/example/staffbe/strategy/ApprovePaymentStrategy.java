package com.example.staffbe.strategy;

import com.example.staffbe.model.Payment;
import com.example.staffbe.repository.PaymentRepository;
import com.example.staffbe.enums.PaymentStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.staffbe.service.PaymentServiceImpl;

import java.util.UUID;

import ch.qos.logback.core.net.SyslogOutputStream;

@Component
public class ApprovePaymentStrategy implements ApprovalStrategy {

    private final PaymentRepository paymentRepository;
    private final PaymentServiceImpl paymentService;

    @Autowired
    public ApprovePaymentStrategy(PaymentRepository paymentRepository, PaymentServiceImpl paymentService) {
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
    }

    @Override
    public void approve(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        paymentService.updatePaymentStatus(paymentId, PaymentStatus.PAID); // BAGIAN INI HARUS DIUBAH MENJADI payment.approvePayment(paymentId) UNTUK MENYESUAIKAN DENGAN REFUND SERVICE IMPL
        paymentRepository.save(payment);  
    }
    

    @Override
    public void reject(UUID paymentId) {
        throw new UnsupportedOperationException("Reject operation is not supported for approve strategy");
    }
}
