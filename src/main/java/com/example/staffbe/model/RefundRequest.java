// package com.example.staffbe.model;

// import lombok.*;
// import java.time.LocalDateTime;
// import java.util.UUID;

// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// public class RefundRequest {
//     private UUID id;
//     private UUID transactionId;
//     private RefundStatus status;

//     public void approveRefund() {
//         this.status = RefundStatus.ACCEPTED;
//     }

//     public void rejectRefund() {
//         this.status = RefundStatus.REJECTED;
//     }
// }