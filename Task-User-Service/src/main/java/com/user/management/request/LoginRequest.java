package com.user.management.request;

public class LoginRequest {

    private String email;     // The user's email address for login.
    private String password;  // The user's password for login.

    // Default constructor
    public LoginRequest() {
    }

    // All-args constructor for creating LoginRequest with email and password
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }
}
