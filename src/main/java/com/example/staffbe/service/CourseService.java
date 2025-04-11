package com.example.staffbe.service;

import com.example.staffbe.model.CourseRequest;
import com.example.staffbe.model.CourseStatus;
import com.example.staffbe.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseRequest> getAllCourseRequests() {
        return courseRepository.findAll();
    }

    public List<CourseRequest> getPendingCourseRequests() {
        return courseRepository.findByStatus(CourseStatus.PENDING.toString());
    }

    public Optional<CourseRequest> getCourseRequestById(UUID courseId) {
        return courseRepository.findById(courseId);
    }

    public void approveCourse(UUID courseId) {
        courseRepository.findById(courseId).ifPresent(request -> {
            request.approveCourse();
            courseRepository.save(request);
        });
    }

    public void rejectCourse(UUID courseId) {
        courseRepository.findById(courseId).ifPresent(request -> {
            request.rejectCourse();
            courseRepository.save(request);
        });
    }
}