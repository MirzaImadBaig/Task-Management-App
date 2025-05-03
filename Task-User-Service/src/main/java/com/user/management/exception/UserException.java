package com.user.management.exception;

// Custom exception class to handle user-related errors
public class UserException extends Exception {
    
    // Constructor to initialize the exception with a custom message
    public UserException(String message) {
        super(message);  // Pass the message to the superclass (Exception)
    }
}
