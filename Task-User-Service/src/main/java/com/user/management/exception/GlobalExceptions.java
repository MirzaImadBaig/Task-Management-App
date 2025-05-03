package com.user.management.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptions {
    
    // Handle custom UserException
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetails> userExceptionHandler(UserException ue, WebRequest req) {
        ErrorDetails error = new ErrorDetails(ue.getMessage(), req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handle BadCredentialsException (e.g., incorrect login attempts)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> badCredentialsExceptionHandler(BadCredentialsException ex, WebRequest req) {
        ErrorDetails error = new ErrorDetails("Invalid credentials", req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED); // 401 Unauthorized
    }
    
    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> otherExceptionHandler(Exception ex, WebRequest req) {
        ErrorDetails error = new ErrorDetails(ex.getMessage(), req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("error", "Forbidden", "message", ex.getMessage()));
    }
}
