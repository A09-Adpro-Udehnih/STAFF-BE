//package com.example.staffbe.service;
//
//import com.example.staffbe.model.RefundRequest;
//import com.example.staffbe.model.RefundStatus;
//import com.example.staffbe.repository.RefundRepository;
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
//class RefundServiceTest {
//
//    @Mock
//    private RefundRepository refundRepository;
//
//    @InjectMocks
//    private RefundService refundService;
//
//    private UUID refundId;
//    private RefundRequest request;
//
//    @BeforeEach
//    void setUp() {
//        refundId = UUID.randomUUID();
//        request = new RefundRequest();
//        request.setId(refundId);
//        request.setStatus(RefundStatus.PENDING);
//    }
//
//    @Test
//    void approveRefund_shouldSetStatusToAccepted() {
//        when(refundRepository.findById(refundId)).thenReturn(Optional.of(request));
//
//        refundService.approveRefund(refundId);
//
//        assertThat(request.getStatus()).isEqualTo(RefundStatus.ACCEPTED);
//        verify(refundRepository).save(request);
//    }
//
//    @Test
//    void rejectRefund_shouldSetStatusToRejected() {
//        when(refundRepository.findById(refundId)).thenReturn(Optional.of(request));
//
//        refundService.rejectRefund(refundId);
//
//        assertThat(request.getStatus()).isEqualTo(RefundStatus.REJECTED);
//        verify(refundRepository).save(request);
//    }
//}
