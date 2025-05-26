package com.example.staffbe.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.staffbe.model.Payment;
import com.example.staffbe.model.Refund;
import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.repository.PaymentRepository;
import com.example.staffbe.repository.RefundRepository;
import com.example.staffbe.repository.TutorApplicationRepository;

public class ResourceServiceTest {

    private RefundRepository refundRepository;
    private TutorApplicationRepository tutorApplicationRepository;
    private PaymentRepository paymentRepository;
    private ResourceService resourceService;

    @BeforeEach
    void setUp() {
        refundRepository = mock(RefundRepository.class);
        tutorApplicationRepository = mock(TutorApplicationRepository.class);
        paymentRepository = mock(PaymentRepository.class);
        resourceService = new ResourceService(refundRepository, tutorApplicationRepository, paymentRepository);
    }

    @Test
    void testGetAllRefundsAsync() throws Exception {
        List<Refund> mockRefunds = List.of(new Refund());
        when(refundRepository.findAll()).thenReturn(mockRefunds);

        CompletableFuture<List<Refund>> future = resourceService.getAllRefundsAsync();
        assertEquals(mockRefunds, future.get());

        verify(refundRepository, times(1)).findAll();
    }

    @Test
    void testGetAllTutorApplicationsAsync() throws Exception {
        List<TutorApplication> mockList = List.of(new TutorApplication());
        when(tutorApplicationRepository.findAll()).thenReturn(mockList);

        CompletableFuture<List<TutorApplication>> future = resourceService.getAllTutorApplicationsAsync();
        assertEquals(mockList, future.get());

        verify(tutorApplicationRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPaymentsAsync() throws Exception {
        List<Payment> mockList = List.of(new Payment());
        when(paymentRepository.findAll()).thenReturn(mockList);

        CompletableFuture<List<Payment>> future = resourceService.getAllPaymentsAsync();
        assertEquals(mockList, future.get());

        verify(paymentRepository, times(1)).findAll();
    }
}
