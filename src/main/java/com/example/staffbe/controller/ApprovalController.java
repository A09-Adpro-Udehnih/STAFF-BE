package com.example.staffbe.controller;

import com.example.staffbe.enums.Role;
import com.example.staffbe.model.User;
import com.example.staffbe.service.ApprovalService;
import com.example.staffbe.service.UserServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/approval")
public class ApprovalController {

    private final ApprovalService approvalService;
    private final UserServiceImpl userService;

    public ApprovalController(ApprovalService approvalService, UserServiceImpl userService) {
        this.approvalService = approvalService;
        this.userService = userService;
    }

    @PostMapping("/approve/{type}/{id}")
    public ResponseEntity<String> approve(@PathVariable("id") UUID id,
                                          @PathVariable("type") String type,
                                          Principal principal) {
        UUID userId = UUID.fromString(principal.getName());

        Optional<User> userOpt = userService.getUserById(userId);

        if (userOpt.isEmpty() || userOpt.get().getRole() != Role.STAFF) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only STAFF is authorized to approve.");
        }

        try {
            approvalService.approve(id, type);
            return ResponseEntity.ok("Approval successful for " + type);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        }
    }

    @PostMapping("/reject/{type}/{id}")
    public ResponseEntity<String> reject(@PathVariable("id") UUID id,
                                         @PathVariable("type") String type,
                                         Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        Optional<User> userOpt = userService.getUserById(userId);

        if (userOpt.isEmpty() || userOpt.get().getRole() != Role.STAFF) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only STAFF is authorized to reject.");
        }

        try {
            approvalService.reject(id, type);
            return ResponseEntity.ok("Rejection successful for " + type);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
        }
    }
}
