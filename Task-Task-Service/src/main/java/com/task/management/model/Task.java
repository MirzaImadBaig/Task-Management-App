package com.task.management.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.task.management.enums.TaskStatus;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Unique identifier for the task

    private String title;  // Title of the task

    private String description;  // Detailed description of the task

    private String image;  // URL or path to an image associated with the task

    @Enumerated(EnumType.STRING)  // Ensures that TaskStatus is saved as a string (e.g., "COMPLETED") instead of its ordinal value (e.g., 0)
    private TaskStatus status;  // Current status of the task (e.g., "IN_PROGRESS", "COMPLETED")

    private LocalDateTime createdAt;  // Timestamp when the task was created

    private LocalDateTime deadline;  // Deadline for the task completion

    private Long assignedUserId;  // User ID to whom the task is assigned

    @ElementCollection  // Defines a list of tags for categorizing tasks
    private List<String> tags = new ArrayList<>();  // List of tags for categorizing tasks

    // Default constructor
    public Task() {
    }

    // Constructor for creating a new Task with specific values
    public Task(Long id, String title, String description, String image, TaskStatus status, LocalDateTime createdAt,
                LocalDateTime deadline, Long assignedUserId, List<String> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.status = status;
        this.createdAt = createdAt;
        this.deadline = deadline;
        this.assignedUserId = assignedUserId;
        this.tags = tags;
    }

    // Getter and setter for ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and setter for image
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Getter and setter for status
    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    // Getter and setter for createdAt
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Getter and setter for deadline
    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    // Getter and setter for assignedUserId
    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    // Getter and setter for tags
    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
