package com.example.staffbe.service;

import com.example.staffbe.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    /**
     * Retrieve user by ID
     * @param id the ID of the user
     * @return Optional containing user if found, otherwise empty
     */
    Optional<User> getUserById(UUID id);
}
