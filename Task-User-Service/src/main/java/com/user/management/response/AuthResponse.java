package com.user.management.response;

public class AuthResponse {

    private String jwt;      // The JWT token generated after successful authentication.
    private boolean status;  // The status of the authentication (true for success, false for failure).
    private String message;  // The message providing more details (e.g., success or error message).

    // Default constructor
    public AuthResponse() {
        super();
    }

    // Constructor with parameters to initialize jwt, status, and message fields
    public AuthResponse(String jwt, boolean status, String message) {
        this.jwt = jwt;
        this.status = status;
        this.message = message;
    }

    // Getter for jwt
    public String getJwt() {
        return jwt;
    }

    // Setter for jwt
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    // Getter for status
    public boolean isStatus() {
        return status;
    }

    // Setter for status
    public void setStatus(boolean status) {
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
}
