package com.example.employeemanagement.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.employeemanagement.dto.RegisterRequest;
import com.example.employeemanagement.dto.UserResponse;
import com.example.employeemanagement.entity.Role;
import com.example.employeemanagement.entity.User;
import com.example.employeemanagement.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void register_shouldRejectDuplicateUsername() {
        RegisterRequest request = new RegisterRequest("user1", "123456", Role.USER);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(new User()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.register(request));

        assertTrue(exception.getMessage().contains("already exists"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void register_shouldEncodePasswordAndReturnResponseWithoutPassword() {
        RegisterRequest request = new RegisterRequest("user1", "123456", Role.USER);
        User savedUser = User.builder()
                .id(1L)
                .username("user1")
                .password("encoded-password")
                .role(Role.USER)
                .build();

        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123456")).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponse response = userService.register(request);

        assertNotNull(response);
        assertEquals("user1", response.getUsername());
        assertEquals(Role.USER, response.getRole());
        assertNull(response.getPassword());
        verify(passwordEncoder).encode("123456");
        verify(userRepository).save(any(User.class));
    }
}
