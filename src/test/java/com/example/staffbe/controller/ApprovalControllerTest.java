package com.example.staffbe.controller;

import com.example.staffbe.enums.Role;
import com.example.staffbe.model.User;
import com.example.staffbe.service.ApprovalService;
import com.example.staffbe.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ApprovalControllerTest {

    @Mock
    private ApprovalService approvalService;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private ApprovalController approvalController;

    private MockMvc mockMvc;
    private UUID id;
    private UUID userId;
    private Principal principal;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(approvalController).build();
        id = UUID.randomUUID();
        userId = UUID.randomUUID();
        principal = () -> userId.toString();
    }

    @Test
    void testApproveAsStaff() throws Exception {
        User mockUser = User.builder().id(userId).role(Role.STAFF).build();
        when(userService.getUserById(userId)).thenReturn(Optional.of(mockUser));

        mockMvc.perform(post("/approval/approve/tutor/{id}", id)
                        .principal(principal))
                .andExpect(status().isOk());

        verify(approvalService, times(1)).approve(id, "tutor");
    }

    @Test
    void testRejectAsStaff() throws Exception {
        User mockUser = User.builder().id(userId).role(Role.STAFF).build();
        when(userService.getUserById(userId)).thenReturn(Optional.of(mockUser));

        mockMvc.perform(post("/approval/reject/refund/{id}", id)
                        .principal(principal))
                .andExpect(status().isOk());

        verify(approvalService, times(1)).reject(id, "refund");
    }

    @Test
    void testApproveForbiddenForNonStaff() throws Exception {
        User mockUser = User.builder().id(userId).role(Role.STUDENT).build();
        when(userService.getUserById(userId)).thenReturn(Optional.of(mockUser));

        mockMvc.perform(post("/approval/approve/tutor/{id}", id)
                        .principal(principal))
                .andExpect(status().isForbidden());

        verify(approvalService, never()).approve(any(), any());
    }

    @Test
    void testRejectForbiddenForNonStaff() throws Exception {
        User mockUser = User.builder().id(userId).role(Role.TUTOR).build();
        when(userService.getUserById(userId)).thenReturn(Optional.of(mockUser));

        mockMvc.perform(post("/approval/reject/refund/{id}", id)
                        .principal(principal))
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
                        .principal(principal))
                .andExpect(status().isNotFound());
    }

    @Test
    void testRejectNotFound() throws Exception {
        User mockUser = User.builder().id(userId).role(Role.STAFF).build();
        when(userService.getUserById(userId)).thenReturn(Optional.of(mockUser));
        doThrow(new RuntimeException("Resource not found"))
                .when(approvalService).reject(id, "refund");

        mockMvc.perform(post("/approval/reject/refund/{id}", id)
                        .principal(principal))
                .andExpect(status().isNotFound());
    }
}
