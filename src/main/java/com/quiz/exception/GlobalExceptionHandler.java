package com.quiz.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Global exception handler for the Quiz application.
 * Handles common exceptions and provides user-friendly error pages.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles IllegalArgumentException - typically thrown for invalid input
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        logger.error("IllegalArgumentException occurred: {}", ex.getMessage());
        model.addAttribute("error", "Invalid request: " + ex.getMessage());
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        return "error";
    }

    /**
     * Handles AccessDeniedException - when user tries to access unauthorized resources
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDeniedException(AccessDeniedException ex, Model model) {
        logger.warn("Access denied: {}", ex.getMessage());
        model.addAttribute("error", "Access Denied: You don't have permission to access this resource.");
        model.addAttribute("status", HttpStatus.FORBIDDEN.value());
        return "error";
    }

    /**
     * Handles AuthenticationException - when authentication fails
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleAuthenticationException(AuthenticationException ex, Model model) {
        logger.warn("Authentication failed: {}", ex.getMessage());
        return "redirect:/login?error";
    }

    /**
     * Handles NoHandlerFoundException - 404 errors
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NoHandlerFoundException ex, Model model) {
        logger.error("Page not found: {}", ex.getRequestURL());
        model.addAttribute("error", "Page not found");
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        return "error";
    }

    /**
     * Handles all other exceptions
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception ex, Model model) {
        logger.error("Unexpected error occurred", ex);
        model.addAttribute("error", "An unexpected error occurred. Please try again later.");
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "error";
    }
}
