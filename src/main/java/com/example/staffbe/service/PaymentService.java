package com.example.staffbe.service;

import com.example.staffbe.model.Transaction;
import com.example.staffbe.model.TransactionStatus;
import com.example.staffbe.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public PaymentService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(UUID transactionId) {
        return transactionRepository.findById(transactionId);
    }

    public void updateTransactionStatus(UUID transactionId, TransactionStatus status) {
        transactionRepository.findById(transactionId).ifPresent(transaction -> {
            transaction.updateStatus(status);
            transactionRepository.save(transaction);
        });
    }
}