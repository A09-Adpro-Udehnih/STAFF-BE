package com.example.staffbe.strategy;

import com.example.staffbe.model.ApplicationStatus;
import com.example.staffbe.model.TutorApplication;

public class TutorApprovalStrategy implements ApprovalStrategy {

    private final TutorApplication application;

    public TutorApprovalStrategy(TutorApplication application) {
        this.application = application;
    }

    @Override
    public void approve() {
        application.setStatus(ApplicationStatus.APPROVED);
    }

    @Override
    public void reject() {
        application.setStatus(ApplicationStatus.REJECTED);
    }
}
