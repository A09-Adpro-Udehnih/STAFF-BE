package com.example.staffbe.dto;

import com.example.staffbe.model.Payment;
import com.example.staffbe.model.Refund;
import com.example.staffbe.model.TutorApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {

    @Test
    @DisplayName("Test GlobalResponse success case")
    void testGlobalResponseSuccess() {
        // Test with String data
        GlobalResponse<String> response = new GlobalResponse<>(HttpStatus.OK, true, "Success message", "data");
        assertTrue(response.isSuccess());
        assertEquals("Success message", response.getMessage());
        assertEquals("data", response.getData());
        assertEquals(HttpStatus.OK, response.getCode());

        // Test with null data
        GlobalResponse<Void> nullResponse = new GlobalResponse<>(HttpStatus.OK, true, "Success with no data", null);
        assertTrue(nullResponse.isSuccess());
        assertEquals("Success with no data", nullResponse.getMessage());
        assertNull(nullResponse.getData());
        assertEquals(HttpStatus.OK, nullResponse.getCode());

        // Test with List data
        List<String> dataList = Arrays.asList("item1", "item2");
        GlobalResponse<List<String>> listResponse = new GlobalResponse<>(HttpStatus.OK, true, "List data", dataList);
        assertTrue(listResponse.isSuccess());
        assertEquals("List data", listResponse.getMessage());
        assertEquals(dataList, listResponse.getData());
        assertEquals(HttpStatus.OK, listResponse.getCode());
    }

    @Test
    @DisplayName("Test GlobalResponse error case")
    void testGlobalResponseError() {
        GlobalResponse<String> errorResponse = new GlobalResponse<>(HttpStatus.BAD_REQUEST, false, "Error message", null);
        assertFalse(errorResponse.isSuccess());
        assertEquals("Error message", errorResponse.getMessage());
        assertNull(errorResponse.getData());
        assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getCode());
    }

    @Test
    @DisplayName("Test GlobalResponse setters")
    void testGlobalResponseSetters() {
        GlobalResponse<String> response = new GlobalResponse<>();
        response.setSuccess(true);
        response.setMessage("Setter test");
        response.setData("test data");
        response.setCode(HttpStatus.OK);

        assertTrue(response.isSuccess());
        assertEquals("Setter test", response.getMessage());
        assertEquals("test data", response.getData());
        assertEquals(HttpStatus.OK, response.getCode());
    }

    @Test
    @DisplayName("Test StaffDashboardResponse")
    void testStaffDashboardResponse() {
        // Create test data
        UUID userId = UUID.randomUUID();
        
        // Create a test payment
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID());
        payment.setUserId(userId);
        payment.setAmount(100.0);
        
        // Create a test refund
        Refund refund = new Refund();
        refund.setId(UUID.randomUUID());
        refund.setPayment(payment);
        refund.setAmount(50.0);
        
        // Create a test tutor application
        TutorApplication tutorApplication = new TutorApplication();
        tutorApplication.setId(UUID.randomUUID());
        tutorApplication.setStudentId(userId);

        // Create lists of test data
        List<Payment> payments = Arrays.asList(payment);
        List<Refund> refunds = Arrays.asList(refund);
        List<TutorApplication> tutorApplications = Arrays.asList(tutorApplication);

        // Create StaffDashboardResponse
        StaffDashboardResponse response = new StaffDashboardResponse(refunds, payments, tutorApplications);

        // Test getters
        assertEquals(payments, response.getPayments());
        assertEquals(refunds, response.getRefunds());
        assertEquals(tutorApplications, response.getTutorApplications());

        // Test with empty lists
        StaffDashboardResponse emptyResponse = new StaffDashboardResponse(
            List.of(), List.of(), List.of()
        );
        assertTrue(emptyResponse.getPayments().isEmpty());
        assertTrue(emptyResponse.getRefunds().isEmpty());
        assertTrue(emptyResponse.getTutorApplications().isEmpty());

        // Test with null lists
        StaffDashboardResponse nullResponse = new StaffDashboardResponse(null, null, null);
        assertNull(nullResponse.getPayments());
        assertNull(nullResponse.getRefunds());
        assertNull(nullResponse.getTutorApplications());
    }

    @Test
    @DisplayName("Test StaffDashboardResponse setters")
    void testStaffDashboardResponseSetters() {
        StaffDashboardResponse response = new StaffDashboardResponse();
        
        List<Payment> payments = Arrays.asList(new Payment());
        List<Refund> refunds = Arrays.asList(new Refund());
        List<TutorApplication> tutorApplications = Arrays.asList(new TutorApplication());

        response.setPayments(payments);
        response.setRefunds(refunds);
        response.setTutorApplications(tutorApplications);

        assertEquals(payments, response.getPayments());
        assertEquals(refunds, response.getRefunds());
        assertEquals(tutorApplications, response.getTutorApplications());
    }
} 