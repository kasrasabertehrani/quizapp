package com.quiz.service;

import com.quiz.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class QuizUserDetailsServiceTest {

    private QuizUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        userDetailsService = new QuizUserDetailsService();
    }

    @Test
    void testRegisterUser_Success() throws Exception {
        userDetailsService.registerUser("testuser", "password123", "USER", "test@example.com");

        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");
        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
    }

    @Test
    void testRegisterUser_ThrowsExceptionWhenUserExists() throws Exception {
        userDetailsService.registerUser("testuser", "password123", "USER", "test@example.com");

        Exception exception = assertThrows(Exception.class, () -> {
            userDetailsService.registerUser("testuser", "password456", "ADMIN", "test2@example.com");
        });

        assertEquals("User already exists", exception.getMessage());
    }

    @Test
    void testLoadUserByUsername_Success() throws Exception {
        userDetailsService.registerUser("john", "securepass", "ADMIN", "john@example.com");

        UserDetails userDetails = userDetailsService.loadUserByUsername("john");
        assertNotNull(userDetails);
        assertEquals("john", userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void testLoadUserByUsername_ThrowsExceptionWhenUserNotFound() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistentuser");
        });
    }

    @Test
    void testRegisterUser_PasswordIsEncrypted() throws Exception {
        String plainPassword = "mypassword";
        userDetailsService.registerUser("alice", plainPassword, "USER", "alice@example.com");

        UserDetails userDetails = userDetailsService.loadUserByUsername("alice");
        // Password should be encrypted (BCrypt hash starts with $2a$ or $2b$)
        assertTrue(userDetails.getPassword().startsWith("$2"));
        assertNotEquals(plainPassword, userDetails.getPassword());
    }

    @Test
    void testRegisterMultipleUsers() throws Exception {
        userDetailsService.registerUser("user1", "pass1", "USER", "user1@test.com");
        userDetailsService.registerUser("user2", "pass2", "ADMIN", "user2@test.com");
        userDetailsService.registerUser("user3", "pass3", "USER", "user3@test.com");

        UserDetails user1 = userDetailsService.loadUserByUsername("user1");
        UserDetails user2 = userDetailsService.loadUserByUsername("user2");
        UserDetails user3 = userDetailsService.loadUserByUsername("user3");

        assertNotNull(user1);
        assertNotNull(user2);
        assertNotNull(user3);
    }

    @Test
    void testUserRoles() throws Exception {
        userDetailsService.registerUser("adminuser", "adminpass", "ADMIN", "admin@test.com");
        userDetailsService.registerUser("normaluser", "userpass", "USER", "user@test.com");

        UserDetails admin = userDetailsService.loadUserByUsername("adminuser");
        UserDetails user = userDetailsService.loadUserByUsername("normaluser");

        assertTrue(admin.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
        assertTrue(user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }
}
