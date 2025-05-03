package com.user.management.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.user.management.model.User;
import com.user.management.repository.UserRepository;
import com.user.management.service.CustomerUserServiceImplementation;

class CustomeUserServiceImplementationTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomerUserServiceImplementation userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // initialize mocks
    }

    @Test
    void testLoadUserByUsername_UserExists_ReturnsUserDetails() {
        // Arrange
        String email = "test@example.com";
        String password = "test123";
        User mockUser = new User();
        mockUser.setEmail(email);
        mockUser.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(mockUser);

        // Act
        UserDetails userDetails = userService.loadUserByUsername(email);

        // Assert
        assertEquals(email, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsername_UserDoesNotExist_ThrowsException() {
        // Arrange
        String email = "notfound@example.com";

        when(userRepository.findByEmail(email)).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(email);
        });
    }
}
