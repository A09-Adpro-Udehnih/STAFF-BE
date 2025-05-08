package com.example.staffbe.strategy;

import com.example.staffbe.model.Payment;
import com.example.staffbe.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentStrategy implements GetAllStrategy<Payment> {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentStrategy(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();  // Mengambil semua data Payment dari database
    }
}
