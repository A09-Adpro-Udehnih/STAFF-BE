package com.example.staffbe.repository;

import com.example.staffbe.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    // Method untuk mendapatkan semua pembayaran
    List<Payment> findAll();

}
