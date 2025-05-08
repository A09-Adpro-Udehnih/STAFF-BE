package com.example.staffbe.strategy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {

    private final RefundStrategy refundStrategy;
    private final TutorApplicationStrategy tutorApplicationStrategy;
    private final PaymentStrategy paymentStrategy;

    @Autowired
    public ResourceService(RefundStrategy refundStrategy,
                           TutorApplicationStrategy tutorApplicationStrategy,
                           PaymentStrategy paymentStrategy) {
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
