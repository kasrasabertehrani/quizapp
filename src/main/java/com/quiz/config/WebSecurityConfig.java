package com.quiz.config;


import com.quiz.service.QuizUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for the Quiz Application.
 * Configures Spring Security with form-based authentication and role-based access control.
 * 
 * Features:
 * - Custom login/registration pages
 * - BCrypt password encryption
 * - Role-based authorization (USER and ADMIN)
 * - CSRF protection enabled
 * - Session management
 *
 * @author Kasra Saber Tehrani
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final QuizUserDetailsService quizUserDetailsService;

    public WebSecurityConfig(QuizUserDetailsService quizUserDetailsService) {
        this.quizUserDetailsService = quizUserDetailsService;
    }

    /**
     * Configures the security filter chain for HTTP requests.
     * Defines access rules for different endpoints based on roles.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/register", "/login").permitAll() // Allow access to registration and login pages
                        .requestMatchers("/quizlist").hasRole("ADMIN") // Restrict /quizlist to users with the ADMIN role
                        .requestMatchers("/quiz").hasRole("USER") // Restrict /quiz to users with the USER role
                        .anyRequest().authenticated() // Require authentication for all other endpoints
                )
                .formLogin(form -> form
                        .loginPage("/login") // Custom login page
                        .defaultSuccessUrl("/home", true) // Redirect to /greet after successful login
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll
                );
        return http.build();
    }

    /**
     * Configures the authentication manager with custom user details service
     * and password encoder.
     *
     * @param http the HttpSecurity object to extract shared objects from
     * @return the configured AuthenticationManager
     * @throws Exception if configuration fails
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(quizUserDetailsService) // Use your custom UserDetailsService
                .passwordEncoder(passwordEncoder()); // Use the password encoder
        return authenticationManagerBuilder.build();
    }

    /**
     * Provides a BCrypt password encoder bean for secure password storage.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}