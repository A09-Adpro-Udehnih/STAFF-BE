package com.example.staffbe.service;

import org.springframework.stereotype.Service;

import com.example.staffbe.strategy.ApprovalContext;

import java.util.UUID;

@Service
public class ApprovalService {

    private final ApprovalContext approvalContext;

    public ApprovalService(ApprovalContext approvalContext) {
        this.approvalContext = approvalContext;
    }

    public void approve(UUID id, String type) {
        approvalContext.approve(id, type);  
    }

    public void reject(UUID id, String type) {
        approvalContext.reject(id, type);  
    }
}
