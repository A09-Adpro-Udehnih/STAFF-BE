package com.example.staffbe.service;

import com.example.staffbe.repository.PaymentRepository;
import com.example.staffbe.enums.PaymentStatus;
import com.example.staffbe.model.Payment;
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
    public int updatePaymentStatus(UUID id, PaymentStatus status) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);

        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            payment.setStatus(status);
            paymentRepository.save(payment);
            return 1; // Update successful
        } else {
            return 0; // Payment not found
        }
    }
}
