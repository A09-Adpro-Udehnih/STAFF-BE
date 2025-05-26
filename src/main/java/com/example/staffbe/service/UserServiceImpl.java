package com.example.staffbe.service;

import com.example.staffbe.model.User;
import com.example.staffbe.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.example.staffbe.enums.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public int updateUserRole(UUID id, Role role) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setRole(role);
            userRepository.save(user);
            return 1; // Update successful
        } else {
            return 0; // User not found
        }
    }
}
