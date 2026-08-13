package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.RegisterRequest;
import com.example.employeemanagement.dto.UserResponse;
import com.example.employeemanagement.entity.User;
import com.example.employeemanagement.exception.UserAlreadyExistsException;
import com.example.employeemanagement.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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

    public String generateToken(Authentication authentication) {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return jwtService.generateToken(principal);
    }
}
