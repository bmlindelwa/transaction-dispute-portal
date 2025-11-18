package com.api.dispute.service;

import com.api.dispute.exception.UsernameNotFoundException;
import com.api.dispute.model.User;
import com.api.dispute.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void testRegisterUser() {
        User user = new User();
        user.setUsername("bule");
        user.setPassword("1234");

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        User savedUser = userService.registerNewUser(user.getUsername(),  user.getPassword());

        assertNotNull(savedUser.getId());
        assertEquals("bule", savedUser.getUsername());
        assertNotEquals("1234", savedUser.getPassword()); // password must be hashed
        assertTrue(passwordEncoder.matches("1234", savedUser.getPassword()));
    }

    @Test
    void testGetUserByUsernameSuccess() {
        User user = new User();
        user.setId(10L);
        user.setUsername("john");

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        User found = userService.findByUsername("john").orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + user.getUsername()));

        assertEquals(10L, found.getId());
        assertEquals("john", found.getUsername());
    }

    @Test
    void testGetUserByUsernameNotFound() {
        when(userRepository.findByUsername("ghost"))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userService.findByUsername("ghost"));
    }
}
