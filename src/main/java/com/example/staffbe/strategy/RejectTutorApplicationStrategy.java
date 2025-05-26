package com.example.staffbe.strategy;

import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.repository.TutorApplicationRepository;
import com.example.staffbe.service.TutorApplicationServiceImpl;
import com.example.staffbe.enums.TutorApplicationStatus;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RejectTutorApplicationStrategy implements ApprovalStrategy {

    private final TutorApplicationRepository tutorApplicationRepository;
    private final TutorApplicationServiceImpl tutorApplicationService;


    
    public RejectTutorApplicationStrategy(TutorApplicationRepository tutorApplicationRepository,TutorApplicationServiceImpl tutorApplicationService) {
        this.tutorApplicationRepository = tutorApplicationRepository;
        this.tutorApplicationService = tutorApplicationService;
    }

    @Override
    public void approve(UUID applicationId) {
        throw new UnsupportedOperationException("Approve operation is not supported for reject strategy");
    }

    @Override
    public void reject(UUID applicationId) {
        TutorApplication tutorApplication = tutorApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Tutor application not found"));

        tutorApplicationService.rejectApplication(applicationId);  // Menyetujui aplikasi tutor
        tutorApplicationRepository.save(tutorApplication);  // Simpan status yang telah diperbarui
    }
}