package com.example.staffbe.dto;

import com.example.staffbe.model.Refund;
import com.example.staffbe.model.Payment;
import com.example.staffbe.model.TutorApplication;

import java.util.List;

public class StaffDashboardResponse {
    private List<Refund> refunds;
    private List<Payment> payments;
    private List<TutorApplication> tutorApplications;

    public StaffDashboardResponse() {
    }

    public StaffDashboardResponse(List<Refund> refunds, List<Payment> payments, List<TutorApplication> tutorApplications) {
        this.refunds = refunds;
        this.payments = payments;
        this.tutorApplications = tutorApplications;
    }

    public List<Refund> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<Refund> refunds) {
        this.refunds = refunds;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<TutorApplication> getTutorApplications() {
        return tutorApplications;
    }

    public void setTutorApplications(List<TutorApplication> tutorApplications) {
        this.tutorApplications = tutorApplications;
    }
}
