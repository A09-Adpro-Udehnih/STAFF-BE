package com.example.staffbe.repository;

import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.enums.TutorApplicationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TutorApplicationRepositoryTest {

    @Autowired
    private TutorApplicationRepository tutorApplicationRepository;

    private TutorApplication testApplication;

    @BeforeEach
    void setUp() {
        UUID studentId = UUID.randomUUID();
        testApplication = TutorApplication.builder()
                .id(UUID.randomUUID())
                .studentId(studentId)
                .status(TutorApplicationStatus.PENDING)
                .build();

        tutorApplicationRepository.save(testApplication); // Simpan aplikasi tutor untuk pengujian
    }

    @Test
    void testFindByStudentId() {
        // Cari aplikasi tutor berdasarkan studentId
        List<TutorApplication> applications = tutorApplicationRepository.findByStudentId(testApplication.getStudentId());

        assertNotNull(applications);
        assertFalse(applications.isEmpty());
        assertEquals(testApplication.getStudentId(), applications.get(0).getStudentId());
    }

    @Test
    void testFindAll() {
        // Ambil semua aplikasi tutor
        List<TutorApplication> applications = tutorApplicationRepository.findAll();

        assertNotNull(applications);
        assertFalse(applications.isEmpty());
        assertTrue(applications.size() >= 1); // Pastikan ada aplikasi yang disimpan
    }
}
