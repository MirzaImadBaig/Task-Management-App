package com.task.management.dtos;

/**
 * Data Transfer Object (DTO) for representing user details
 * without exposing sensitive internal entities.
 */
public class UserDto {
    
    private Long id;
    private String fullName;
    private String email;
    private String mobile;
    private String role;

    // Default constructor
    public UserDto() {
        super();
    }

    // Parameterized constructor
    public UserDto(Long id, String fullName, String email, String mobile, String role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.mobile = mobile;
        this.role = role;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
