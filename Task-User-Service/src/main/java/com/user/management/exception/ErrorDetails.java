package com.user.management.exception;

import java.time.LocalDateTime;

public class ErrorDetails {
    
    private String error;      // Error description
    private String message;    // Error message
    private LocalDateTime timestamp; // Timestamp when the error occurred

    // Constructor to initialize ErrorDetails
    public ErrorDetails(String error, String message, LocalDateTime timestamp) {
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Default constructor
    public ErrorDetails() {
        super();
    }

    // Getter for 'error'
    public String getError() {
        return error;
    }

    // Setter for 'error'
    public void setError(String error) {
        this.error = error;
    }

    // Getter for 'message'
    public String getMessage() {
        return message;
    }

    // Setter for 'message'
    public void setMessage(String message) {
        this.message = message;
    }

    // Getter for 'timestamp'
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Setter for 'timestamp'
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
