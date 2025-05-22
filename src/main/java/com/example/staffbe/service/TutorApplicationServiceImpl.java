package com.example.staffbe.service;

import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.repository.TutorApplicationRepository;
import com.example.staffbe.enums.TutorApplicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.staffbe.enums.Role;
import com.example.staffbe.service.UserService;

import java.util.List;
import java.util.UUID;

@Service
public class TutorApplicationServiceImpl implements TutorApplicationService {

    private final TutorApplicationRepository tutorApplicationRepository;
    private final UserService userService;

    @Autowired
    public TutorApplicationServiceImpl(TutorApplicationRepository tutorApplicationRepository, UserService userService) {
        this.tutorApplicationRepository = tutorApplicationRepository;
        this.userService = userService;
    }

    @Override
    public void approveApplication(UUID applicationId) {
        TutorApplication tutorApplication = tutorApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Tutor application not found"));

        // Ubah status aplikasi tutor menjadi ACCEPTED
        tutorApplication.setStatus(TutorApplicationStatus.ACCEPTED);
        tutorApplicationRepository.save(tutorApplication);  // Simpan perubahan status aplikasi

        // Update role user menjadi TUTOR
        userService.updateUserRole(tutorApplication.getStudentId(), Role.TUTOR);
    }

    @Override
    public void rejectApplication(UUID applicationId) {
        TutorApplication tutorApplication = tutorApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Tutor application not found"));

        // Ubah status aplikasi tutor menjadi REJECTED
        tutorApplication.setStatus(TutorApplicationStatus.DENIED);
        tutorApplicationRepository.save(tutorApplication);  // Simpan perubahan status aplikasi
    }

    @Override
    public List<TutorApplication> findAllApplications() {
        // Mengambil semua aplikasi tutor
        return tutorApplicationRepository.findAll();
    }

    @Override
    public List<TutorApplication> findByStudentId(UUID studentId) {
        // Mengambil aplikasi tutor berdasarkan studentId
        return tutorApplicationRepository.findByStudentId(studentId);
    }
}
