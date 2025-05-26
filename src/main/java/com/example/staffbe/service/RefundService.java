package com.example.staffbe.service;

import com.example.staffbe.model.Refund;
import com.example.staffbe.enums.RefundStatus;

import java.util.List;
import java.util.UUID;

public interface RefundService {

    // Method untuk meng-approve refund
    void approveRefund(UUID refundId);

    // Method untuk menolak refund
    void rejectRefund(UUID refundId);

    // Method untuk mengambil refund berdasarkan id
    Refund findRefundById(UUID refundId);

    // Method untuk mengambil semua refund
    List<Refund> findAllRefunds();
}
