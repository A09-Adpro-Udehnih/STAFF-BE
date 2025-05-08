package com.example.staffbe.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.staffbe.strategy.ApprovalService;

import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApprovalController.class)
@Import(TestSecurityConfig.class)  // Mengimpor konfigurasi keamanan untuk pengujian
public class ApprovalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApprovalService approvalService;

    @InjectMocks
    private ApprovalController approvalController;

    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
    }

    @Test
    @WithMockUser  // Menambahkan anotasi @WithMockUser untuk menyimulasikan pengguna yang sudah terautentikasi
    void testApprove() throws Exception {
        mockMvc.perform(post("/api/approval/approve/tutor/{id}", id))
                .andExpect(status().isOk());  // Mengharapkan status 200 OK

        verify(approvalService, times(1)).approve(id, "tutor");
    }

    @Test
    @WithMockUser  // Menambahkan anotasi @WithMockUser untuk menyimulasikan pengguna yang sudah terautentikasi
    void testReject() throws Exception {
        mockMvc.perform(post("/api/approval/reject/refund/{id}", id))
                .andExpect(status().isOk());  // Mengharapkan status 200 OK

        verify(approvalService, times(1)).reject(id, "refund");
    }

    @Test
    void testApproveNotFound() throws Exception {
        // Simulasi bahwa ID tidak ditemukan
        doThrow(new RuntimeException("Resource not found")).when(approvalService).approve(id, "tutor");

        mockMvc.perform(post("/api/approval/approve/tutor/{id}", id))
                .andExpect(status().isNotFound());  // Mengharapkan status 404 Not Found
    }

    @Test
    void testRejectNotFound() throws Exception {
        // Simulasi bahwa ID tidak ditemukan
        doThrow(new RuntimeException("Resource not found")).when(approvalService).reject(id, "refund");

        mockMvc.perform(post("/api/approval/reject/refund/{id}", id))
                .andExpect(status().isNotFound());  // Mengharapkan status 404 Not Found
    }

}
