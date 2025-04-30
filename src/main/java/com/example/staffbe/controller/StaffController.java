// package com.example.staffbe.controller;

// import com.example.staffbe.model.*;
// import com.example.staffbe.service.*;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import java.util.List;
// import java.util.UUID;

// @RestController
// @RequestMapping("/api/staff")
// @CrossOrigin(origins = "*") // Enable CORS for frontend integration
// public class StaffController {

//     private final TutorService tutorService;
//     private final CourseService courseService;
//     private final PaymentService paymentService;
//     private final RefundService refundService;

//     @Autowired
//     public StaffController(
//             TutorService tutorService,
//             CourseService courseService,
//             PaymentService paymentService,
//             RefundService refundService) {
//         this.tutorService = tutorService;
//         this.courseService = courseService;
//         this.paymentService = paymentService;
//         this.refundService = refundService;
//     }

//     // Tutor Application Endpoints
//     @GetMapping("/tutors")
//     public ResponseEntity<List<TutorApplication>> getTutorApplicants() {
//         List<TutorApplication> tutors = tutorService.getAllTutorApplications();
//         return ResponseEntity.ok(tutors);
//     }

//     @GetMapping("/tutors/pending")
//     public ResponseEntity<List<TutorApplication>> getPendingTutorApplicants() {
//         List<TutorApplication> pendingTutors = tutorService.getPendingTutorApplications();
//         return ResponseEntity.ok(pendingTutors);
//     }

//     @GetMapping("/tutors/{id}")
//     public ResponseEntity<TutorApplication> getTutorApplicationById(@PathVariable UUID id) {
//         return tutorService.getTutorApplicationById(id)
//                 .map(ResponseEntity::ok)
//                 .orElse(ResponseEntity.notFound().build());
//     }

//     @PostMapping("/tutors/{id}/approve")
//     public ResponseEntity<Void> approveTutor(@PathVariable UUID id) {
//         tutorService.approveTutor(id);
//         return ResponseEntity.ok().build();
//     }

//     @PostMapping("/tutors/{id}/reject")
//     public ResponseEntity<Void> rejectTutor(@PathVariable UUID id) {
//         tutorService.rejectTutor(id);
//         return ResponseEntity.ok().build();
//     }

//     // Course Request Endpoints
//     @GetMapping("/courses")
//     public ResponseEntity<List<CourseRequest>> getCourseRequests() {
//         List<CourseRequest> courses = courseService.getAllCourseRequests();
//         return ResponseEntity.ok(courses);
//     }

//     @GetMapping("/courses/pending")
//     public ResponseEntity<List<CourseRequest>> getPendingCourseRequests() {
//         List<CourseRequest> pendingCourses = courseService.getPendingCourseRequests();
//         return ResponseEntity.ok(pendingCourses);
//     }

//     @GetMapping("/courses/{id}")
//     public ResponseEntity<CourseRequest> getCourseRequestById(@PathVariable UUID id) {
//         return courseService.getCourseRequestById(id)
//                 .map(ResponseEntity::ok)
//                 .orElse(ResponseEntity.notFound().build());
//     }

//     @PostMapping("/courses/{id}/approve")
//     public ResponseEntity<Void> approveCourse(@PathVariable UUID id) {
//         courseService.approveCourse(id);
//         return ResponseEntity.ok().build();
//     }

//     @PostMapping("/courses/{id}/reject")
//     public ResponseEntity<Void> rejectCourse(@PathVariable UUID id) {
//         courseService.rejectCourse(id);
//         return ResponseEntity.ok().build();
//     }

//     // Transaction Endpoints
//     @GetMapping("/transactions")
//     public ResponseEntity<List<Transaction>> getPaymentData() {
//         List<Transaction> transactions = paymentService.getAllTransactions();
//         return ResponseEntity.ok(transactions);
//     }

//     @GetMapping("/transactions/{id}")
//     public ResponseEntity<Transaction> getTransactionById(@PathVariable UUID id) {
//         return paymentService.getTransactionById(id)
//                 .map(ResponseEntity::ok)
//                 .orElse(ResponseEntity.notFound().build());
//     }

//     @PostMapping("/transactions/{id}/update-status")
//     public ResponseEntity<Void> updateTransactionStatus(
//             @PathVariable UUID id,
//             @RequestParam TransactionStatus status) {
//         paymentService.updateTransactionStatus(id, status);
//         return ResponseEntity.ok().build();
//     }

//     // Refund Request Endpoints
//     @GetMapping("/refunds")
//     public ResponseEntity<List<RefundRequest>> getRefundRequests() {
//         List<RefundRequest> refunds = refundService.getAllRefundRequests();
//         return ResponseEntity.ok(refunds);
//     }

//     @GetMapping("/refunds/pending")
//     public ResponseEntity<List<RefundRequest>> getPendingRefundRequests() {
//         List<RefundRequest> pendingRefunds = refundService.getPendingRefundRequests();
//         return ResponseEntity.ok(pendingRefunds);
//     }

//     @GetMapping("/refunds/{id}")
//     public ResponseEntity<RefundRequest> getRefundRequestById(@PathVariable UUID id) {
//         return refundService.getRefundRequestById(id)
//                 .map(ResponseEntity::ok)
//                 .orElse(ResponseEntity.notFound().build());
//     }

//     @PostMapping("/refunds/{id}/approve")
//     public ResponseEntity<Void> approveRefund(@PathVariable UUID id) {
//         refundService.approveRefund(id);
//         return ResponseEntity.ok().build();
//     }

//     @PostMapping("/refunds/{id}/reject")
//     public ResponseEntity<Void> rejectRefund(@PathVariable UUID id) {
//         refundService.rejectRefund(id);
//         return ResponseEntity.ok().build();
//     }

//     // Dashboard Summary Endpoint
//     @GetMapping("/dashboard/summary")
//     public ResponseEntity<DashboardSummary> getDashboardSummary() {
//         // Create a summary object with counts of pending items
//         DashboardSummary summary = new DashboardSummary(
//                 tutorService.getPendingTutorApplications().size(),
//                 courseService.getPendingCourseRequests().size(),
//                 refundService.getPendingRefundRequests().size()
//         );
//         return ResponseEntity.ok(summary);
//     }

//     // Inner class for dashboard summary data
//     @Data
//     @AllArgsConstructor
//     static class DashboardSummary {
//         private int pendingTutorApplications;
//         private int pendingCourseRequests;
//         private int pendingRefundRequests;
//     }
// }