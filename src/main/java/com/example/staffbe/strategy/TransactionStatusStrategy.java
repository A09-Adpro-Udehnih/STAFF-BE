package com.example.staffbe.strategy;

import com.example.staffbe.model.Transaction;
import com.example.staffbe.model.TransactionStatus;

public class TransactionStatusStrategy implements ApprovalStrategy {
    private final Transaction transaction;

    public TransactionStatusStrategy(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void approve() {
        transaction.setStatus(TransactionStatus.PAID);
    }

    @Override
    public void reject() {
        transaction.setStatus(TransactionStatus.FAILED); // atau CANCELED, sesuaikan dengan enum-mu
    }
}
