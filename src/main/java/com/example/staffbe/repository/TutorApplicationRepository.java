package com.example.staffbe.repository;

import com.example.staffbe.model.TutorApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TutorApplicationRepository extends JpaRepository<TutorApplication, UUID> {

    // Method untuk mencari TutorApplication berdasarkan studentId
    List<TutorApplication> findByStudentId(UUID studentId);

    // Method untuk mengambil semua TutorApplication
    List<TutorApplication> findAll();
}
