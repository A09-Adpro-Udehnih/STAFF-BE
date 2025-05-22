package com.example.staffbe.strategy;

import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.repository.TutorApplicationRepository;
import com.example.staffbe.service.TutorApplicationServiceImpl;
import com.example.staffbe.enums.RefundStatus;
import com.example.staffbe.enums.TutorApplicationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.UUID;

public class ApproveTutorApplicationStrategyTest {

    @Mock
    private TutorApplicationRepository tutorApplicationRepository;

    @Mock
    private TutorApplicationServiceImpl tutorApplicationService;

    @InjectMocks
    private ApproveTutorApplicationStrategy approveTutorApplicationStrategy;

    private UUID applicationId;
    private TutorApplication mockTutorApplication;

    @BeforeEach
    void setUp() {

        // Initialize mocks
        MockitoAnnotations.openMocks(this);
        applicationId = UUID.randomUUID();
        mockTutorApplication = TutorApplication.builder()
                .id(applicationId)
                .status(TutorApplicationStatus.PENDING)  // Status sebelum approve
                .build();
    }

    @Test
    void testApproveTutorApplication() {
        // Simulasi behavior repository untuk menemukan tutor application
        when(tutorApplicationRepository.findById(applicationId)).thenReturn(java.util.Optional.of(mockTutorApplication));

        doAnswer(invocation -> {
            mockTutorApplication.setStatus(TutorApplicationStatus.ACCEPTED);  // Mengubah status refund menjadi APPROVED
            return null;
        }).when(tutorApplicationRepository).save(mockTutorApplication);

        // Panggil approve untuk tutor application
        approveTutorApplicationStrategy.approve(applicationId);

        verify(tutorApplicationService, times(1)).approveApplication(applicationId);

        // Verifikasi bahwa status tutor application terupdate menjadi ACCEPTED
        assertEquals(TutorApplicationStatus.ACCEPTED, mockTutorApplication.getStatus());  // Pastikan statusnya terupdate menjadi ACCEPTED

        // Verifikasi bahwa save() dipanggil pada repository untuk menyimpan perubahan status
        verify(tutorApplicationRepository, times(1)).save(mockTutorApplication);  // Memastikan bahwa save dipanggil sekali
    }
}
