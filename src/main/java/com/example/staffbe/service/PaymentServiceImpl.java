package com.example.staffbe.service;

import com.example.staffbe.repository.PaymentRepository;
import com.example.staffbe.enums.PaymentStatus;
import com.example.staffbe.enums.RefundStatus;
import com.example.staffbe.model.Payment;
import com.example.staffbe.model.Refund;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    @Transactional
    public void updatePaymentStatus(UUID id, PaymentStatus status) {
        Payment payment = paymentRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Payment not found"));

        // Ubah status refund menjadi ACCEPTED
        payment.setStatus(status);
        paymentRepository.save(payment);
    }
}
