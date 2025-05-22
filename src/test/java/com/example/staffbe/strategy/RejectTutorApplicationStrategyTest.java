package com.example.staffbe.strategy;

import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.repository.TutorApplicationRepository;
import com.example.staffbe.service.TutorApplicationServiceImpl;
import com.example.staffbe.enums.TutorApplicationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.UUID;

public class RejectTutorApplicationStrategyTest {

    @Mock
    private TutorApplicationRepository tutorApplicationRepository;  // Mocking TutorApplicationRepository

    @Mock
    private TutorApplicationServiceImpl tutorApplicationService;  // Mocking TutorApplicationServiceImpl

    @InjectMocks
    private RejectTutorApplicationStrategy rejectTutorApplicationStrategy;  // Injecting mocks into RejectTutorApplicationStrategy

    private UUID applicationId;
    private TutorApplication mockTutorApplication;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        applicationId = UUID.randomUUID();  // Simulasi ID tutor application yang akan direject
        mockTutorApplication = TutorApplication.builder()
                .id(applicationId)
                .status(TutorApplicationStatus.PENDING)  // Status sebelum reject
                .build();
    }

    @Test
    void testRejectTutorApplication() {
        // Simulasi behavior repository untuk menemukan tutor application
        when(tutorApplicationRepository.findById(applicationId)).thenReturn(java.util.Optional.of(mockTutorApplication));

        // Simulasi behavior untuk mengubah status tutor application menjadi DENIED
        doAnswer(invocation -> {
            mockTutorApplication.setStatus(TutorApplicationStatus.DENIED);  // Mengubah status tutor application menjadi DENIED
            return null;
        }).when(tutorApplicationRepository).save(mockTutorApplication);  // Simulasi pemanggilan save() pada repository

        // Panggil reject untuk tutor application
        rejectTutorApplicationStrategy.reject(applicationId);

        // Verifikasi bahwa status tutor application terupdate menjadi DENIED
        assertEquals(TutorApplicationStatus.DENIED, mockTutorApplication.getStatus());  // Pastikan statusnya terupdate menjadi DENIED

        // Verifikasi bahwa rejectTutorApplication dipanggil dengan ID yang benar
        verify(tutorApplicationService, times(1)).rejectApplication(applicationId);

        // Verifikasi bahwa save() dipanggil pada repository untuk menyimpan perubahan status
        verify(tutorApplicationRepository, times(1)).save(mockTutorApplication);  // Memastikan bahwa save dipanggil sekali
    }
}
