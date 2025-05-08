package com.example.staffbe.strategy;

import com.example.staffbe.model.Refund;
import com.example.staffbe.repository.RefundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RefundStrategy implements GetAllStrategy<Refund> {

    private final RefundRepository refundRepository;

    @Autowired
    public RefundStrategy(RefundRepository refundRepository) {
        this.refundRepository = refundRepository;
    }

    @Override
    public List<Refund> findAll() {
        return refundRepository.findAll();  // Mengambil semua data Refund dari database
    }
}