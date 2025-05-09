package com.example.staffbe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.staffbe.strategy.GetAllPaymentStrategy;
import com.example.staffbe.strategy.GetAllRefundStrategy;
import com.example.staffbe.strategy.GetAllTutorApplicationStrategy;

@Service
public class ResourceService {

    private final GetAllRefundStrategy refundStrategy;
    private final GetAllTutorApplicationStrategy tutorApplicationStrategy;
    private final GetAllPaymentStrategy paymentStrategy;

    @Autowired
    public ResourceService(GetAllRefundStrategy refundStrategy,
                           GetAllTutorApplicationStrategy tutorApplicationStrategy,
                           GetAllPaymentStrategy paymentStrategy) {
        this.refundStrategy = refundStrategy;
        this.tutorApplicationStrategy = tutorApplicationStrategy;
        this.paymentStrategy = paymentStrategy;
    }

    public <T> List<T> getAll(String type) {
        switch (type) {
            case "refund":
                return (List<T>) refundStrategy.findAll();
            case "tutorApplication":
                return (List<T>) tutorApplicationStrategy.findAll();
            case "payment":
                return (List<T>) paymentStrategy.findAll();
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
    }
}
