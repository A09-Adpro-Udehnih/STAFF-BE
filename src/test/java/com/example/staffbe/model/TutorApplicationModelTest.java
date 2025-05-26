package com.example.staffbe.model;

import com.example.staffbe.enums.TutorApplicationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TutorApplicationModelTest {

    private TutorApplication tutorApplication;

    @BeforeEach
    void setUp() {
        tutorApplication = TutorApplication.builder()
                .id(UUID.randomUUID())
                .studentId(UUID.randomUUID())
                .status(TutorApplicationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testTutorApplicationBuilderAndGetters() {
        UUID tutorApplicationId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        TutorApplicationStatus status = TutorApplicationStatus.PENDING;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        TutorApplication tutorApplication = TutorApplication.builder()
                .id(tutorApplicationId)
                .studentId(studentId)
                .status(status)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        assertEquals(tutorApplicationId, tutorApplication.getId());
        assertEquals(studentId, tutorApplication.getStudentId());
        assertEquals(status, tutorApplication.getStatus());
        assertEquals(createdAt, tutorApplication.getCreatedAt());
        assertEquals(updatedAt, tutorApplication.getUpdatedAt());
    }

    @Test
    void testTutorApplicationSetters() {
        TutorApplication tutorApplication = new TutorApplication();
        UUID tutorApplicationId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        TutorApplicationStatus status = TutorApplicationStatus.PENDING;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        tutorApplication.setId(tutorApplicationId);
        tutorApplication.setStudentId(studentId);
        tutorApplication.setStatus(status);
        tutorApplication.setCreatedAt(createdAt);
        tutorApplication.setUpdatedAt(updatedAt);

        // Verify the values are correctly set using setters
        assertEquals(tutorApplicationId, tutorApplication.getId());
        assertEquals(studentId, tutorApplication.getStudentId());
        assertEquals(status, tutorApplication.getStatus());
        assertEquals(createdAt, tutorApplication.getCreatedAt());
        assertEquals(updatedAt, tutorApplication.getUpdatedAt());
    }

    @Test
    void testEquals() {
        UUID tutorApplicationId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID(); // Menggunakan UUID yang sama untuk perbandingan
    
        TutorApplication tutorApplication1 = TutorApplication.builder()
                .id(tutorApplicationId)
                .studentId(studentId)  // Menggunakan studentId yang sama
                .status(TutorApplicationStatus.PENDING)
                .build();
    
        TutorApplication tutorApplication2 = TutorApplication.builder()
                .id(tutorApplicationId)
                .studentId(studentId)  // Menggunakan studentId yang sama
                .status(TutorApplicationStatus.PENDING)
                .build();
    
        TutorApplication tutorApplication3 = TutorApplication.builder()
                .id(UUID.randomUUID())
                .studentId(UUID.randomUUID())  // studentId berbeda
                .status(TutorApplicationStatus.ACCEPTED)
                .build();
    
        // Pastikan tutorApplication1 dan tutorApplication2 dianggap sama
        assertEquals(tutorApplication1, tutorApplication2);
    
        // Pastikan tutorApplication1 dan tutorApplication3 tidak sama
        assertNotEquals(tutorApplication1, tutorApplication3);
        assertNotEquals(tutorApplication2, tutorApplication3);
    }

    @Test
    void testPrePersist() {
        TutorApplication tutorApplication = new TutorApplication();
        
        tutorApplication.onCreate();

        assertNotNull(tutorApplication.getCreatedAt());
        assertNotNull(tutorApplication.getUpdatedAt());
        
        LocalDateTime now = LocalDateTime.now();
        assertTrue(java.time.temporal.ChronoUnit.SECONDS.between(tutorApplication.getCreatedAt(), now) < 2);
        assertTrue(java.time.temporal.ChronoUnit.SECONDS.between(tutorApplication.getUpdatedAt(), now) < 2);
    }

    @Test
    void testPreUpdate() {
        TutorApplication tutorApplication = new TutorApplication();
        tutorApplication.setCreatedAt(LocalDateTime.now().minusDays(1)); 
        LocalDateTime oldUpdatedAt = LocalDateTime.now().minusHours(1);
        tutorApplication.setUpdatedAt(oldUpdatedAt);
        
        tutorApplication.onUpdate();
        
        assertNotEquals(oldUpdatedAt, tutorApplication.getUpdatedAt());
        
        LocalDateTime now = LocalDateTime.now();
        assertTrue(java.time.temporal.ChronoUnit.SECONDS.between(tutorApplication.getUpdatedAt(), now) < 2);
    }

}
