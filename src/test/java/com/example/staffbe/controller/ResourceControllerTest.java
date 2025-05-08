package com.example.staffbe.controller;

import com.example.staffbe.strategy.ResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.mockito.Mockito.*;

import java.util.UUID;

@WebMvcTest(ResourceController.class)
@Import(TestSecurityConfig.class)
public class ResourceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResourceService resourceService;

    @InjectMocks
    private ResourceController resourceController;

    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
    }

    @Test
    void testGetAllRefunds() throws Exception {
        mockMvc.perform(get("/api/resources?type=refund"))
                .andExpect(status().isOk())  // Mengharapkan status 200 OK
                .andExpect(content().contentType("application/json"));
        
        verify(resourceService, times(1)).getAll("refund");  // Verifikasi bahwa resourceService.getAll dipanggil dengan "refund"
    }

    @Test
    void testGetAllTutorApplications() throws Exception {
        mockMvc.perform(get("/api/resources?type=tutorApplication"))
                .andExpect(status().isOk())  // Mengharapkan status 200 OK
                .andExpect(content().contentType("application/json"));
        
        verify(resourceService, times(1)).getAll("tutorApplication");  // Verifikasi bahwa resourceService.getAll dipanggil dengan "tutorApplication"
    }

    @Test
    void testGetAllPayments() throws Exception {
        mockMvc.perform(get("/api/resources?type=payment"))
                .andExpect(status().isOk())  // Mengharapkan status 200 OK
                .andExpect(content().contentType("application/json"));
        
        verify(resourceService, times(1)).getAll("payment");  // Verifikasi bahwa resourceService.getAll dipanggil dengan "payment"
    }
}
