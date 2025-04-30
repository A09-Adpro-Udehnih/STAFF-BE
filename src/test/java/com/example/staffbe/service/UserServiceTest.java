package com.example.staffbe.service;

import com.example.staffbe.enums.Role;
import com.example.staffbe.model.User;
import com.example.staffbe.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Untuk mengaktifkan Mockito
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .email("test@example.com")
                .fullName("Test User")
                .password("password123")
                .role(Role.STAFF)  // Set role sesuai enum yang ada
                .build();
    }

    @Test
    void testGetUserById() {
        // Simulasi repository untuk mengembalikan user saat findById dipanggil
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        Optional<User> retrievedUser = userService.getUserById(testUser.getId());

        assertTrue(retrievedUser.isPresent()); // Pastikan user ditemukan
        assertEquals(testUser.getId(), retrievedUser.get().getId());
        assertEquals(testUser.getEmail(), retrievedUser.get().getEmail());
    }

    @Test
    void testGetUserById_UserNotFound() {
        UUID randomId = UUID.randomUUID();
        
        when(userRepository.findById(randomId)).thenReturn(Optional.empty());

        Optional<User> retrievedUser = userService.getUserById(randomId);

        assertFalse(retrievedUser.isPresent()); // Pastikan user tidak ditemukan
    }
}
