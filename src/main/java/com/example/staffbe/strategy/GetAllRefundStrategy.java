package com.example.staffbe.strategy;

import com.example.staffbe.model.Refund;
import com.example.staffbe.repository.RefundRepository;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllRefundStrategy implements GetAllStrategy<Refund> {

    private final RefundRepository refundRepository;

    
    public GetAllRefundStrategy(RefundRepository refundRepository) {
        this.refundRepository = refundRepository;
    }

    @Override
    public List<Refund> findAll() {
        return refundRepository.findAll();  // Mengambil semua data Refund dari database
    }
}