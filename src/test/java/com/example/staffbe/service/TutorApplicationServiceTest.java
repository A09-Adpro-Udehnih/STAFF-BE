package com.example.staffbe.service;

import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.enums.TutorApplicationStatus;
import com.example.staffbe.repository.TutorApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.staffbe.service.UserService;
import com.example.staffbe.enums.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TutorApplicationServiceTest {

    @Mock
    private TutorApplicationRepository tutorApplicationRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TutorApplicationServiceImpl tutorApplicationService;

    private TutorApplication testApplication;
    private UUID applicationId;

    @BeforeEach
    void setUp() {
        applicationId = UUID.randomUUID();
        testApplication = TutorApplication.builder()
                .id(applicationId)
                .studentId(UUID.randomUUID())
                .status(TutorApplicationStatus.PENDING)
                .build();
    }

    @Test
    void testApproveApplication() {
        when(tutorApplicationRepository.findById(applicationId)).thenReturn(Optional.of(testApplication));

        tutorApplicationService.approveApplication(applicationId);

        // Verifikasi status aplikasi telah diperbarui menjadi ACCEPTED
        assertEquals(TutorApplicationStatus.ACCEPTED, testApplication.getStatus());
        verify(userService, times(1)).updateUserRole(testApplication.getStudentId(), Role.TUTOR);
    }

    @Test
    void testRejectApplication() {
        when(tutorApplicationRepository.findById(applicationId)).thenReturn(Optional.of(testApplication));

        tutorApplicationService.rejectApplication(applicationId);

        // Verifikasi status aplikasi telah diperbarui menjadi DENIED
        assertEquals(TutorApplicationStatus.DENIED, testApplication.getStatus());
        verify(userService, never()).updateUserRole(any(), any());  // Tidak ada perubahan role user
    }

    @Test
    void testFindAllApplications() {
        when(tutorApplicationRepository.findAll()).thenReturn(List.of(testApplication));

        List<TutorApplication> applications = tutorApplicationService.findAllApplications();

        assertNotNull(applications);
        assertFalse(applications.isEmpty());
        assertEquals(1, applications.size());
        assertEquals(testApplication, applications.get(0));
    }

    @Test
    void testFindByStudentId() {
        when(tutorApplicationRepository.findByStudentId(testApplication.getStudentId())).thenReturn(List.of(testApplication));

        List<TutorApplication> applications = tutorApplicationService.findByStudentId(testApplication.getStudentId());

        assertNotNull(applications);
        assertFalse(applications.isEmpty());
        assertEquals(testApplication.getStudentId(), applications.get(0).getStudentId());
    }
}
