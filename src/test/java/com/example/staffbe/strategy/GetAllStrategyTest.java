package com.example.staffbe.strategy;

import com.example.staffbe.model.Payment;
import com.example.staffbe.model.Refund;
import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.repository.PaymentRepository;
import com.example.staffbe.repository.RefundRepository;
import com.example.staffbe.repository.TutorApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetAllStrategyTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private RefundRepository refundRepository;

    @Mock
    private TutorApplicationRepository tutorApplicationRepository;

    private GetAllPaymentStrategy getAllPaymentStrategy;
    private GetAllRefundStrategy getAllRefundStrategy;
    private GetAllTutorApplicationStrategy getAllTutorApplicationStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        getAllPaymentStrategy = new GetAllPaymentStrategy(paymentRepository);
        getAllRefundStrategy = new GetAllRefundStrategy(refundRepository);
        getAllTutorApplicationStrategy = new GetAllTutorApplicationStrategy(tutorApplicationRepository);
    }

    @Test
    void testGetAllPaymentStrategy() {
        // Arrange
        Payment payment1 = new Payment();
        Payment payment2 = new Payment();
        List<Payment> expectedPayments = Arrays.asList(payment1, payment2);
        when(paymentRepository.findAll()).thenReturn(expectedPayments);

        // Act
        List<Payment> result = getAllPaymentStrategy.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(expectedPayments, result);
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void testGetAllRefundStrategy() {
        // Arrange
        Refund refund1 = new Refund();
        Refund refund2 = new Refund();
        List<Refund> expectedRefunds = Arrays.asList(refund1, refund2);
        when(refundRepository.findAll()).thenReturn(expectedRefunds);

        // Act
        List<Refund> result = getAllRefundStrategy.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(expectedRefunds, result);
        verify(refundRepository, times(1)).findAll();
    }

    @Test
    void testGetAllTutorApplicationStrategy() {
        // Arrange
        TutorApplication app1 = new TutorApplication();
        TutorApplication app2 = new TutorApplication();
        List<TutorApplication> expectedApplications = Arrays.asList(app1, app2);
        when(tutorApplicationRepository.findAll()).thenReturn(expectedApplications);

        // Act
        List<TutorApplication> result = getAllTutorApplicationStrategy.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(expectedApplications, result);
        verify(tutorApplicationRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPaymentStrategyEmptyList() {
        // Arrange
        when(paymentRepository.findAll()).thenReturn(List.of());

        // Act
        List<Payment> result = getAllPaymentStrategy.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void testGetAllRefundStrategyEmptyList() {
        // Arrange
        when(refundRepository.findAll()).thenReturn(List.of());

        // Act
        List<Refund> result = getAllRefundStrategy.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(refundRepository, times(1)).findAll();
    }

    @Test
    void testGetAllTutorApplicationStrategyEmptyList() {
        // Arrange
        when(tutorApplicationRepository.findAll()).thenReturn(List.of());

        // Act
        List<TutorApplication> result = getAllTutorApplicationStrategy.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(tutorApplicationRepository, times(1)).findAll();
    }
} 