package com.example.staffbe.strategy;

import com.example.staffbe.model.Payment;
import com.example.staffbe.repository.PaymentRepository;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllPaymentStrategy implements GetAllStrategy<Payment> {

    private final PaymentRepository paymentRepository;

    
    public GetAllPaymentStrategy(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();  // Mengambil semua data Payment dari database
    }
}
