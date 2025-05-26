package com.example.staffbe.service;

import com.example.staffbe.model.Refund;
import com.example.staffbe.model.Payment;
import com.example.staffbe.enums.RefundStatus;
import com.example.staffbe.enums.PaymentStatus;
import com.example.staffbe.repository.RefundRepository;
import com.example.staffbe.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class RefundServiceImpl implements RefundService {

    private final RefundRepository refundRepository;
    private final PaymentRepository paymentRepository;

    public RefundServiceImpl(RefundRepository refundRepository, PaymentRepository paymentRepository) {
        this.refundRepository = refundRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public void approveRefund(UUID refundId) {
        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new RuntimeException("Refund not found"));

        // Ubah status refund menjadi ACCEPTED
        refund.setStatus(RefundStatus.ACCEPTED);
        refundRepository.save(refund);

        // Dapatkan payment terkait dan ubah status menjadi REFUNDED
        Payment payment = refund.getPayment();
        payment.setStatus(PaymentStatus.PAID); // Mengubah status payment
        paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public void rejectRefund(UUID refundId) {
        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new RuntimeException("Refund not found"));

        // Ubah status refund menjadi REJECTED
        refund.setStatus(RefundStatus.REJECTED);
        refundRepository.save(refund);
    }

    @Override
    public Refund findRefundById(UUID refundId) {
        return refundRepository.findById(refundId)
                .orElseThrow(() -> new RuntimeException("Refund not found"));
    }

    @Override
    public List<Refund> findAllRefunds() {
        return refundRepository.findAll();
    }
}
