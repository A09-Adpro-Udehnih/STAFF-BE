package com.example.staffbe.service;

import com.example.staffbe.enums.PaymentStatus;
import com.example.staffbe.model.Payment;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    // Method untuk mendapatkan semua pembayaran
    List<Payment> getAllPayments();

    // Method untuk memperbarui status pembayaran
    int updatePaymentStatus(UUID id, PaymentStatus status);
}
