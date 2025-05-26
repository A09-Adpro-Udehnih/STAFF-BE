package com.example.staffbe.repository;

import com.example.staffbe.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.staffbe.enums.Role;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Membuat user untuk pengujian
        testUser = User.builder()
                .email("test@example.com")
                .fullName("Test User")
                .password("password123")
                .role(Role.STAFF)
                .build();
        
        // Simpan user di database untuk pengujian
        userRepository.save(testUser);
    }

    @Test
    void testFindById() {
        // Ambil user yang sudah disimpan berdasarkan ID
        Optional<User> retrievedUser = userRepository.findById(testUser.getId());

        assertTrue(retrievedUser.isPresent()); // Pastikan user ditemukan
        assertEquals(testUser.getId(), retrievedUser.get().getId());
        assertEquals(testUser.getEmail(), retrievedUser.get().getEmail());
    }

    @Test
    void testFindById_UserNotFound() {
        // Coba ambil user dengan ID yang tidak ada
        UUID randomId = UUID.randomUUID();
        Optional<User> retrievedUser = userRepository.findById(randomId);

        assertFalse(retrievedUser.isPresent()); // Pastikan tidak ditemukan
    }
}
