package com.submission.management.dtos;

import java.time.LocalDateTime;

/**
 * DTO class for transferring Task data between layers.
 * This class is not persisted in the database.
 */
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private String image;
    private String status;  // Could be enum, currently handled as String
    private LocalDateTime createdAt;
    private LocalDateTime deadline;

    // Default constructor
    public TaskDto() {
        // No-args constructor
    }

    // All-args constructor
    public TaskDto(Long id, String title, String description, String image, String status,
                   LocalDateTime createdAt, LocalDateTime deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.status = status;
        this.createdAt = createdAt;
        this.deadline = deadline;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
