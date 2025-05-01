package com.example.staffbe.repository;

import com.example.staffbe.model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface RefundRepository extends JpaRepository<Refund, UUID> {

    // Method untuk mencari refund berdasarkan payment_id
    List<Refund> findByPaymentId(UUID paymentId);

}