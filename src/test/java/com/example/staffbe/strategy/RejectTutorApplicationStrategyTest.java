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

class RejectTutorApplicationStrategyTest {

    @Mock
    private TutorApplicationRepository tutorApplicationRepository;

    @Mock
    private TutorApplicationServiceImpl tutorApplicationService;

    private RejectTutorApplicationStrategy rejectTutorApplicationStrategy;
    private UUID applicationId;
    private TutorApplication mockTutorApplication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rejectTutorApplicationStrategy = new RejectTutorApplicationStrategy(tutorApplicationRepository, tutorApplicationService);
        applicationId = UUID.randomUUID();
        mockTutorApplication = new TutorApplication();
        mockTutorApplication.setId(applicationId);
    }

    @Test
    void testRejectTutorApplication() {
        // Arrange
        when(tutorApplicationRepository.findById(applicationId)).thenReturn(Optional.of(mockTutorApplication));
        when(tutorApplicationRepository.save(any(TutorApplication.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        rejectTutorApplicationStrategy.reject(applicationId);

        // Assert
        verify(tutorApplicationRepository, times(1)).findById(applicationId);
        verify(tutorApplicationService, times(1)).rejectApplication(applicationId);
        verify(tutorApplicationRepository, times(1)).save(any(TutorApplication.class));
    }

    @Test
    void testRejectTutorApplicationNotFound() {
        // Arrange
        when(tutorApplicationRepository.findById(applicationId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            rejectTutorApplicationStrategy.reject(applicationId);
        });

        verify(tutorApplicationRepository, times(1)).findById(applicationId);
        verify(tutorApplicationService, never()).rejectApplication(any());
        verify(tutorApplicationRepository, never()).save(any(TutorApplication.class));
    }

    @Test
    void testRejectTutorApplicationWithNullId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            rejectTutorApplicationStrategy.reject(null);
        });

        verify(tutorApplicationRepository, never()).findById(any());
        verify(tutorApplicationService, never()).rejectApplication(any());
        verify(tutorApplicationRepository, never()).save(any(TutorApplication.class));
    }

    @Test
    void testApproveNotSupported() {
        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> {
            rejectTutorApplicationStrategy.approve(applicationId);
        });

        verify(tutorApplicationRepository, never()).findById(any());
        verify(tutorApplicationService, never()).rejectApplication(any());
        verify(tutorApplicationRepository, never()).save(any(TutorApplication.class));
    }
}
