// package com.example.staffbe.model;

// import lombok.*;
// import java.util.UUID;

// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// public class CourseRequest {
//     private UUID id;
//     private UUID tutorId;
//     private String courseTitle;
//     private CourseStatus status;

//     public void approveCourse() {
//         this.status = CourseStatus.APPROVED;
//     }

//     public void rejectCourse() {
//         this.status = CourseStatus.REJECTED;
//     }
// }