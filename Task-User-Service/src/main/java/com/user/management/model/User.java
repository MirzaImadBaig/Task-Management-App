package com.user.management.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String email;
    private String mobile;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String role = "ROLE_CUSTOMER";

    private List<Long> completedTasks;

    // Default constructor
    public User() {
    }

    // All-args constructor
    public User(Long id, String fullName, String email, String mobile, String password, String role, List<Long> completedTasks) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.role = role;
        this.completedTasks = completedTasks;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Long> getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(List<Long> completedTasks) {
        this.completedTasks = completedTasks;
    }
}
