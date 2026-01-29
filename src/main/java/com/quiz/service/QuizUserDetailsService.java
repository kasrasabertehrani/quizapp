package com.quiz.service;

import com.quiz.exception.UserAlreadyExistsException;
import com.quiz.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class QuizUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(QuizUserDetailsService.class);
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final PasswordEncoder passwordEncoder;

    public QuizUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Attempting to load user: {}", username);
        User user = users.get(username);
        if (user == null) {
            logger.warn("User not found: {}", username);
            throw new UsernameNotFoundException("User not found");
        }
        logger.debug("User found: {} with role: {}", username, user.getRole());
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
    public void registerUser(String username, String password, String role, String email) {
        if(users.containsKey(username)) {
            logger.warn("Registration failed: User already exists - {}", username);
            throw new UserAlreadyExistsException("User already exists: " + username);
        }
        String encodedPassword = passwordEncoder.encode(password);
        users.put(username, new User(username, encodedPassword, role, email));
        logger.info("New user registered: {} with role: {}", username, role);
    }

}
