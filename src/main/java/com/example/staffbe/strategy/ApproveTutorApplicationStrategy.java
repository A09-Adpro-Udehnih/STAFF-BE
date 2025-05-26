package com.example.staffbe.strategy;

import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.repository.TutorApplicationRepository;
import com.example.staffbe.service.TutorApplicationServiceImpl;
import com.example.staffbe.enums.TutorApplicationStatus;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ApproveTutorApplicationStrategy implements ApprovalStrategy {

    private final TutorApplicationRepository tutorApplicationRepository;
    private final TutorApplicationServiceImpl tutorApplicationService;

    
    public ApproveTutorApplicationStrategy(TutorApplicationRepository tutorApplicationRepository,TutorApplicationServiceImpl tutorApplicationservice) {
        this.tutorApplicationRepository = tutorApplicationRepository;
        this.tutorApplicationService = tutorApplicationservice;
    }

    @Override
    public void approve(UUID applicationId) {
        TutorApplication tutorApplication = tutorApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Tutor application not found"));

        tutorApplicationService.approveApplication(applicationId);  // Menyetujui aplikasi tutor
        tutorApplicationRepository.save(tutorApplication);  // Simpan status yang telah diperbarui
    }

    @Override
    public void reject(UUID applicationId) {
        throw new UnsupportedOperationException("Reject operation is not supported for approve strategy");
    }
}
