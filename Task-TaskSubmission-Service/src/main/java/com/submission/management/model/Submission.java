package com.submission.management.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a Submission.
 */
@Entity
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long taskId;

    private String githubLink;

    private Long userId;

    // Default value is set here; for reliability, it’s better to also ensure it's set in constructors
    private String status = "PENDING";

    private LocalDateTime submissionTime;

    // Default no-args constructor
    public Submission() {
        this.status = "PENDING";
    }

    // Parameterized constructor
    public Submission(Long id, Long taskId, String githubLink, Long userId, String status, LocalDateTime submissionTime) {
        this.id = id;
        this.taskId = taskId;
        this.githubLink = githubLink;
        this.userId = userId;
        this.status = status;
        this.submissionTime = submissionTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(LocalDateTime submissionTime) {
        this.submissionTime = submissionTime;
    }
}
