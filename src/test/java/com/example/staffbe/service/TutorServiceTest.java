package com.example.staffbe.service;

import com.example.staffbe.model.ApplicationStatus;
import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.repository.TutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@org.junit.jupiter.api.extension.ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @Mock
    private TutorRepository tutorRepository;

    @InjectMocks
    private TutorService tutorService;

    private UUID tutorId;
    private TutorApplication application;

    @BeforeEach
    void setUp() {
        tutorId = UUID.randomUUID();
        application = new TutorApplication();
        application.setId(tutorId);
        application.setStatus(ApplicationStatus.PENDING);
        application.setStudentId(UUID.randomUUID());
    }

    @Test
    void approveTutor_shouldSetStatusToApproved() {
        when(tutorRepository.findById(tutorId)).thenReturn(Optional.of(application));

        tutorService.approveTutor(tutorId);

        assertThat(application.getStatus()).isEqualTo(ApplicationStatus.APPROVED);
        verify(tutorRepository).save(application);
    }

    @Test
    void rejectTutor_shouldSetStatusToRejected() {
        when(tutorRepository.findById(tutorId)).thenReturn(Optional.of(application));

        tutorService.rejectTutor(tutorId);

        assertThat(application.getStatus()).isEqualTo(ApplicationStatus.REJECTED);
        verify(tutorRepository).save(application);
    }
}
