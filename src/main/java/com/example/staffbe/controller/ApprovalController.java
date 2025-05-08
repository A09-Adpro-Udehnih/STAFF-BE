package com.example.staffbe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.staffbe.strategy.ApprovalService;

import java.util.UUID;

@RestController
@RequestMapping("/api/approval")
public class ApprovalController {

    private final ApprovalService approvalService;

    @Autowired
    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @PostMapping("/approve/{type}/{id}")
    public ResponseEntity<String> approve(@PathVariable("id") UUID id, @PathVariable("type") String type) {
        try {
            approvalService.approve(id, type);
            return ResponseEntity.ok("Approval successful for " + type);
        } catch (RuntimeException e) {  // Catching exception for not found
            return ResponseEntity.status(404).body("Resource not found: " + e.getMessage());  // 404 Not Found
        }
    }

    @PostMapping("/reject/{type}/{id}")
    public ResponseEntity<String> reject(@PathVariable("id") UUID id, @PathVariable("type") String type) {
        try {
            approvalService.reject(id, type);
            return ResponseEntity.ok("Rejection successful for " + type);
        } catch (RuntimeException e) {  // Catching exception for not found
            return ResponseEntity.status(404).body("Resource not found: " + e.getMessage());  // 404 Not Found
        }
    }
}
