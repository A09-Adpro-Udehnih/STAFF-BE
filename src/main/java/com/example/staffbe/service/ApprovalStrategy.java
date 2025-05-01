package com.example.staffbe.service;

import java.util.UUID;

public interface ApprovalStrategy {
    void approve(UUID id);  // Method untuk approve

    void reject(UUID id);  // Method untuk reject
}
