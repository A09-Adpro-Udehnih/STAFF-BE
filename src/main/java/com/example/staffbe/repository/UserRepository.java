package com.example.staffbe.repository;

import com.example.staffbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // Kita bisa menambahkan query tambahan jika diperlukan, misalnya:
    // Optional<User> findByEmail(String email);
}
