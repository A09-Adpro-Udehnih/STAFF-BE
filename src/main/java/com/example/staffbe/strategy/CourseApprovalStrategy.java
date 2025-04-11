package com.example.staffbe.strategy;

import com.example.staffbe.model.CourseRequest;
import com.example.staffbe.model.CourseStatus;

public class CourseApprovalStrategy implements ApprovalStrategy {
    private final CourseRequest request;

    public CourseApprovalStrategy(CourseRequest request) {
        this.request = request;
    }

    @Override
    public void approve() {
        request.setStatus(CourseStatus.APPROVED);
    }

    @Override
    public void reject() {
        request.setStatus(CourseStatus.REJECTED);
    }
}
