package com.example.staffbe.strategy;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.staffbe.model.Payment;
import com.example.staffbe.model.Refund;
import com.example.staffbe.model.TutorApplication;

@ExtendWith(MockitoExtension.class)
public class ResourceServiceTest {

    @Mock
    private RefundStrategy refundStrategy;

    @Mock
    private TutorApplicationStrategy tutorApplicationStrategy;

    @Mock
    private PaymentStrategy paymentStrategy;

    private ResourceService resourceService;

    @BeforeEach
    public void setUp() {
        resourceService = new ResourceService(refundStrategy, tutorApplicationStrategy, paymentStrategy);
    }

    @Test
    public void testGetAllRefunds() {
        // Prepare
        List<Refund> expectedRefunds = Arrays.asList(new Refund(), new Refund());
        when(refundStrategy.findAll()).thenReturn(expectedRefunds);

        // Execute
        List<Refund> result = resourceService.getAll("refund");

        // Verify
        assertEquals(expectedRefunds, result);
        verify(refundStrategy).findAll();
        verifyNoInteractions(tutorApplicationStrategy, paymentStrategy);
    }

    @Test
    public void testGetAllTutorApplications() {
        // Prepare
        List<TutorApplication> expectedApplications = Arrays.asList(new TutorApplication(), new TutorApplication());
        when(tutorApplicationStrategy.findAll()).thenReturn(expectedApplications);

        // Execute
        List<TutorApplication> result = resourceService.getAll("tutorApplication");

        // Verify
        assertEquals(expectedApplications, result);
        verify(tutorApplicationStrategy).findAll();
        verifyNoInteractions(refundStrategy, paymentStrategy);
    }

    @Test
    public void testGetAllPayments() {
        // Prepare
        List<Payment> expectedPayments = Arrays.asList(new Payment(), new Payment());
        when(paymentStrategy.findAll()).thenReturn(expectedPayments);

        // Execute
        List<Payment> result = resourceService.getAll("payment");

        // Verify
        assertEquals(expectedPayments, result);
        verify(paymentStrategy).findAll();
        verifyNoInteractions(refundStrategy, tutorApplicationStrategy);
    }

    @Test
    public void testGetAllWithInvalidType() {
        // Execute & Verify
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            resourceService.getAll("invalidType");
        });

        assertEquals("Invalid type: invalidType", exception.getMessage());
        verifyNoInteractions(refundStrategy, tutorApplicationStrategy, paymentStrategy);
    }
}