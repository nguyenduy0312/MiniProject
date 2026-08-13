package com.example.employeemanagement.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.employeemanagement.dto.RegisterRequest;
import com.example.employeemanagement.dto.UserResponse;
import com.example.employeemanagement.entity.User;
import com.example.employeemanagement.exception.UserAlreadyExistsException;
import com.example.employeemanagement.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse register(RegisterRequest request) {
        String username = request.username();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException(username);
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .role(request.role())
                .build();

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .role(savedUser.getRole())
                .build();
    }
}
