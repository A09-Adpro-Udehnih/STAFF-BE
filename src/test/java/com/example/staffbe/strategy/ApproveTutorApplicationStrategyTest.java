package com.example.staffbe.strategy;

import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.repository.TutorApplicationRepository;
import com.example.staffbe.service.TutorApplicationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApproveTutorApplicationStrategyTest {

    @Mock
    private TutorApplicationRepository tutorApplicationRepository;

    @Mock
    private TutorApplicationServiceImpl tutorApplicationService;

    private ApproveTutorApplicationStrategy approveTutorApplicationStrategy;
    private UUID applicationId;
    private TutorApplication mockTutorApplication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        approveTutorApplicationStrategy = new ApproveTutorApplicationStrategy(tutorApplicationRepository, tutorApplicationService);
        applicationId = UUID.randomUUID();
        mockTutorApplication = new TutorApplication();
        mockTutorApplication.setId(applicationId);
    }

    @Test
    void testApproveTutorApplication() {
        // Arrange
        when(tutorApplicationRepository.findById(applicationId)).thenReturn(Optional.of(mockTutorApplication));
        when(tutorApplicationRepository.save(any(TutorApplication.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        approveTutorApplicationStrategy.approve(applicationId);

        // Assert
        verify(tutorApplicationRepository, times(1)).findById(applicationId);
        verify(tutorApplicationService, times(1)).approveApplication(applicationId);
        verify(tutorApplicationRepository, times(1)).save(any(TutorApplication.class));
    }

    @Test
    void testApproveTutorApplicationNotFound() {
        // Arrange
        when(tutorApplicationRepository.findById(applicationId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            approveTutorApplicationStrategy.approve(applicationId);
        });

        verify(tutorApplicationRepository, times(1)).findById(applicationId);
        verify(tutorApplicationService, never()).approveApplication(any());
        verify(tutorApplicationRepository, never()).save(any(TutorApplication.class));
    }

    @Test
    void testApproveTutorApplicationWithNullId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            approveTutorApplicationStrategy.approve(null);
        });

        verify(tutorApplicationRepository, never()).findById(any());
        verify(tutorApplicationService, never()).approveApplication(any());
        verify(tutorApplicationRepository, never()).save(any(TutorApplication.class));
    }

    @Test
    void testRejectNotSupported() {
        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> {
            approveTutorApplicationStrategy.reject(applicationId);
        });

        verify(tutorApplicationRepository, never()).findById(any());
        verify(tutorApplicationService, never()).approveApplication(any());
        verify(tutorApplicationRepository, never()).save(any(TutorApplication.class));
    }
}
