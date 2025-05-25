package com.example.staffbe.service;

import com.example.staffbe.model.Refund;
import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.model.Payment;
import com.example.staffbe.repository.RefundRepository;
import com.example.staffbe.repository.TutorApplicationRepository;
import com.example.staffbe.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ResourceService {

    private final RefundRepository refundRepository;
    private final TutorApplicationRepository tutorApplicationRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public ResourceService(RefundRepository refundRepository,
                           TutorApplicationRepository tutorApplicationRepository,
                           PaymentRepository paymentRepository) {
        this.refundRepository = refundRepository;
        this.tutorApplicationRepository = tutorApplicationRepository;
        this.paymentRepository = paymentRepository;
    }

    @Async
    public CompletableFuture<List<Refund>> getAllRefundsAsync() {
        return CompletableFuture.completedFuture(refundRepository.findAll());
    }

    @Async
    public CompletableFuture<List<TutorApplication>> getAllTutorApplicationsAsync() {
        return CompletableFuture.completedFuture(tutorApplicationRepository.findAll());
    }

    @Async
    public CompletableFuture<List<Payment>> getAllPaymentsAsync() {
        return CompletableFuture.completedFuture(paymentRepository.findAll());
    }
}
