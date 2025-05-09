package com.example.staffbe.strategy;

import com.example.staffbe.model.TutorApplication;
import com.example.staffbe.repository.TutorApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllTutorApplicationStrategy implements GetAllStrategy<TutorApplication> {

    private final TutorApplicationRepository tutorApplicationRepository;

    @Autowired
    public GetAllTutorApplicationStrategy(TutorApplicationRepository tutorApplicationRepository) {
        this.tutorApplicationRepository = tutorApplicationRepository;
    }

    @Override
    public List<TutorApplication> findAll() {
        return tutorApplicationRepository.findAll();  // Mengambil semua data TutorApplication dari database
    }
}
