package com.example.staffbe.strategy;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;

class ApprovalContextTest {

    @Mock
    private ApproveTutorApplicationStrategy approveTutorApplicationStrategy;

    @Mock
    private RejectTutorApplicationStrategy rejectTutorApplicationStrategy;

    @Mock
    private ApproveRefundStrategy approveRefundStrategy;

    @Mock
    private RejectRefundStrategy rejectRefundStrategy;

    @Mock
    private ApprovePaymentStrategy approvePaymentStrategy;

    @Mock
    private RejectPaymentStrategy rejectPaymentStrategy;

    private ApprovalContext approvalContext;

    private UUID id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Manually create the ApprovalContext with our mocks
        approvalContext = new ApprovalContext(
            approveTutorApplicationStrategy,
            rejectTutorApplicationStrategy,
            approveRefundStrategy,
            rejectRefundStrategy,
            approvePaymentStrategy,
            rejectPaymentStrategy
        );
        id = UUID.randomUUID();
    }

    @Test
    void testApproveTutorApplication() {
        // Simulasi approve tutor application
        approvalContext.approve(id, "tutorApplication");

        // Verifikasi apakah approve tutor strategy dipanggil
        verify(approveTutorApplicationStrategy, times(1)).approve(id);
    }

    @Test
    void testRejectTutorApplication() {
        // Simulasi reject tutor application
        approvalContext.reject(id, "tutorApplication");

        // Verifikasi apakah reject tutor strategy dipanggil
        verify(rejectTutorApplicationStrategy, times(1)).reject(id);
    }

    @Test
    void testApproveRefund() {
        // Simulasi approve refund
        approvalContext.approve(id, "refund");

        // Verifikasi apakah approve refund strategy dipanggil
        verify(approveRefundStrategy, times(1)).approve(id);
    }

    @Test
    void testRejectRefund() {
        // Simulasi reject refund
        approvalContext.reject(id, "refund");

        // Verifikasi apakah reject refund strategy dipanggil
        verify(rejectRefundStrategy, times(1)).reject(id);
    }

    @Test
    void testApprovePayment() {
        // Simulasi approve payment
        approvalContext.approve(id, "payment");

        // Verifikasi apakah approve payment strategy dipanggil
        verify(approvePaymentStrategy, times(1)).approve(id);
    }

    @Test
    void testRejectPayment() {
        // Simulasi reject payment
        approvalContext.reject(id, "payment");

        // Verifikasi apakah reject payment strategy dipanggil
        verify(rejectPaymentStrategy, times(1)).reject(id);
    }
}