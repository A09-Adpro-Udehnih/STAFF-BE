package com.example.staffbe.service;

import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.repository.TutorApplicationRepository;
import com.example.staffbe.enums.TutorApplicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RejectTutorApplicationStrategy implements ApprovalStrategy {

    private final TutorApplicationRepository tutorApplicationRepository;

    @Autowired
    public RejectTutorApplicationStrategy(TutorApplicationRepository tutorApplicationRepository) {
        this.tutorApplicationRepository = tutorApplicationRepository;
    }

    @Override
    public void approve(UUID applicationId) {
        throw new UnsupportedOperationException("Approve operation is not supported for reject strategy");
    }

    @Override
    public void reject(UUID applicationId) {
        TutorApplication tutorApplication = tutorApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Tutor application not found"));

        tutorApplication.setStatus(TutorApplicationStatus.DENIED);  // Menolak aplikasi tutor
        tutorApplicationRepository.save(tutorApplication);  // Simpan status yang telah diperbarui
    }
}