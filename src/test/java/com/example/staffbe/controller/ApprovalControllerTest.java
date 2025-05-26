package com.example.staffbe.controller;

import com.example.staffbe.enums.Role;
import com.example.staffbe.model.User;
import com.example.staffbe.service.ApprovalService;
import com.example.staffbe.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApprovalController.class)
@Import(TestSecurityConfig.class)
public class ApprovalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApprovalService approvalService;

    @MockBean
    private UserServiceImpl userService;

    private UUID id;
    private UUID userId;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        userId = UUID.randomUUID();
    }

    private Principal mockPrincipal() {
        return () -> userId.toString();
    }

    @Test
    void testApproveAsStaff() throws Exception {
        User mockUser = User.builder().id(userId).role(Role.STAFF).build();
        when(userService.getUserById(userId)).thenReturn(Optional.of(mockUser));

        mockMvc.perform(post("/approval/approve/tutor/{id}", id)
                        .principal(mockPrincipal()))
                .andExpect(status().isOk());

        verify(approvalService, times(1)).approve(id, "tutor");
    }

    @Test
    void testRejectAsStaff() throws Exception {
        User mockUser = User.builder().id(userId).role(Role.STAFF).build();
        when(userService.getUserById(userId)).thenReturn(Optional.of(mockUser));

        mockMvc.perform(post("/approval/reject/refund/{id}", id)
                        .principal(mockPrincipal()))
                .andExpect(status().isOk());

        verify(approvalService, times(1)).reject(id, "refund");
    }

    @Test
    void testApproveForbiddenForNonStaff() throws Exception {
        User mockUser = User.builder().id(userId).role(Role.STUDENT).build();
        when(userService.getUserById(userId)).thenReturn(Optional.of(mockUser));

        mockMvc.perform(post("/approval/approve/tutor/{id}", id)
                        .principal(mockPrincipal()))
                .andExpect(status().isForbidden());

        verify(approvalService, never()).approve(any(), any());
    }

    @Test
    void testRejectForbiddenForNonStaff() throws Exception {
        User mockUser = User.builder().id(userId).role(Role.TUTOR).build();
        when(userService.getUserById(userId)).thenReturn(Optional.of(mockUser));

        mockMvc.perform(post("/approval/reject/refund/{id}", id)
                        .principal(mockPrincipal()))
                .andExpect(status().isForbidden());

        verify(approvalService, never()).reject(any(), any());
    }

    @Test
    void testApproveNotFound() throws Exception {
        User mockUser = User.builder().id(userId).role(Role.STAFF).build();
        when(userService.getUserById(userId)).thenReturn(Optional.of(mockUser));
        doThrow(new RuntimeException("Resource not found"))
                .when(approvalService).approve(id, "tutor");

        mockMvc.perform(post("/approval/approve/tutor/{id}", id)
                        .principal(mockPrincipal()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testRejectNotFound() throws Exception {
        User mockUser = User.builder().id(userId).role(Role.STAFF).build();
        when(userService.getUserById(userId)).thenReturn(Optional.of(mockUser));
        doThrow(new RuntimeException("Resource not found"))
                .when(approvalService).reject(id, "refund");

        mockMvc.perform(post("/approval/reject/refund/{id}", id)
                        .principal(mockPrincipal()))
                .andExpect(status().isNotFound());
    }
}
