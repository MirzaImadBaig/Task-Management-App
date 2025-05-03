package com.submission.management.dtos;

/**
 * DTO class representing user data for transmission between layers.
 * Not an entity, just used to carry data.
 */
public class UserDto {
    private Long id;
    private String fullName;
    private String email;
    private String mobile;
    private String role;

    // No-args constructor
    public UserDto() {
        // Empty constructor
    }

    // All-args constructor
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
