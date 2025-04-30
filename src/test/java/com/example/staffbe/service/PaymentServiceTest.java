//package com.example.staffbe.service;
//
//import com.example.staffbe.model.Transaction;
//import com.example.staffbe.model.TransactionStatus;
//import com.example.staffbe.repository.TransactionRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//@org.junit.jupiter.api.extension.ExtendWith(MockitoExtension.class)
//class PaymentServiceTest {
//
//    @Mock
//    private TransactionRepository transactionRepository;
//
//    @InjectMocks
//    private PaymentService paymentService;
//
//    private UUID transactionId;
//    private Transaction transaction;
//
//    @BeforeEach
//    void setUp() {
//        transactionId = UUID.randomUUID();
//        transaction = new Transaction();
//        transaction.setId(transactionId);
//        transaction.setStatus(TransactionStatus.PENDING);
//        transaction.setAmount(300.0);
//    }
//
//    @Test
//    void updateTransactionStatus_shouldSetStatusToPaid() {
//        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
//
//        paymentService.updateTransactionStatus(transactionId, TransactionStatus.PAID);
//
//        assertThat(transaction.getStatus()).isEqualTo(TransactionStatus.PAID);
//        verify(transactionRepository).save(transaction);
//    }
//
//    @Test
//    void updateTransactionStatus_shouldSetStatusToFailed_whenStatusIsNotPaid() {
//        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
//
//        paymentService.updateTransactionStatus(transactionId, TransactionStatus.FAILED);
//
//        assertThat(transaction.getStatus()).isEqualTo(TransactionStatus.FAILED);
//        verify(transactionRepository).save(transaction);
//    }
//}
