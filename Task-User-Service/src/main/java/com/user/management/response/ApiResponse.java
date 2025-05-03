package com.user.management.response;

public class ApiResponse {

    private String message;  // The response message (e.g., success or failure message).
    private boolean status;  // The status of the response (e.g., true for success, false for failure).

    // Default constructor
    public ApiResponse() {
        super();
    }

    // Constructor with parameters to initialize the message and status fields
    public ApiResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    // Getter for message
    public String getMessage() {
        return message;
    }

    // Setter for message
    public void setMessage(String message) {
        this.message = message;
    }

    // Getter for status
    public boolean isStatus() {
        return status;
    }

    // Setter for status
    public void setStatus(boolean status) {
        this.status = status;
    }
}
