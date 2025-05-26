package com.example.staffbe.service;

import com.example.staffbe.model.TutorApplication;

import java.util.List;
import java.util.UUID;

public interface TutorApplicationService {

    // Method untuk meng-approve aplikasi tutor
    void approveApplication(UUID applicationId);

    // Method untuk menolak aplikasi tutor
    void rejectApplication(UUID applicationId);

    // Method untuk mendapatkan semua aplikasi tutor
    List<TutorApplication> findAllApplications();

    // Method untuk mencari aplikasi tutor berdasarkan studentId
    List<TutorApplication> findByStudentId(UUID studentId);
}
