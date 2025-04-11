package com.example.staffbe.service;

import com.example.staffbe.model.ApplicationStatus;
import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.repository.TutorRepository;
import com.example.staffbe.strategy.ApprovalStrategy;
import com.example.staffbe.strategy.TutorApprovalStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TutorService {
    private final TutorRepository tutorRepository;

    @Autowired
    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    public List<TutorApplication> getAllTutorApplications() {
        return tutorRepository.findAll();
    }

    public List<TutorApplication> getPendingTutorApplications() {
        return tutorRepository.findByStatus(ApplicationStatus.PENDING.toString());
    }

    public Optional<TutorApplication> getTutorApplicationById(UUID tutorId) {
        return tutorRepository.findById(tutorId);
    }

    public void approveTutor(UUID tutorId) {
        tutorRepository.findById(tutorId).ifPresent(application -> {
            ApprovalStrategy strategy = new TutorApprovalStrategy(application);
            strategy.approve();
            tutorRepository.save(application);
        });
    }

    public void rejectTutor(UUID tutorId) {
        tutorRepository.findById(tutorId).ifPresent(application -> {
            ApprovalStrategy strategy = new TutorApprovalStrategy(application);
            strategy.reject();
            tutorRepository.save(application);
        });
    }
}
