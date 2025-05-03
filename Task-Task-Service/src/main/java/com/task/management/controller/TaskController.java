package com.task.management.controller;

import com.task.management.dtos.UserDto;
import com.task.management.enums.TaskStatus;
import com.task.management.model.Task;
import com.task.management.service.TaskService;
import com.task.management.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing tasks.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    /**
     * Create a new task. Only allowed for ADMIN users.
     */
    @PostMapping
    public ResponseEntity<Task> createTask(
            @RequestBody Task task,
            @RequestHeader("Authorization") String jwt) throws Exception {

        if (!StringUtils.hasText(jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDto user = userService.getUserProfileHandler(jwt);
        Task createdTask = taskService.createTask(task, user.getRole());
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    /**
     * Get a task by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {

        if (!StringUtils.hasText(jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    /**
     * Get tasks assigned to the authenticated user.
     */
    @GetMapping("/user")
    public ResponseEntity<List<Task>> getAssignedUsersTask(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) String sortByDeadline,
            @RequestParam(required = false) String sortByCreatedAt) throws Exception {

        if (!StringUtils.hasText(jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDto user = userService.getUserProfileHandler(jwt);
        List<Task> tasks = taskService.assignedUsersTask(user.getId(), status, sortByDeadline, sortByCreatedAt);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get all tasks (admin or global view).
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) String sortByDeadline,
            @RequestParam(required = false) String sortByCreatedAt) throws Exception {

        if (!StringUtils.hasText(jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Task> tasks = taskService.getAllTasks(status, sortByDeadline, sortByCreatedAt);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Assign a task to a user.
     */
    @PutMapping("/{id}/user/{userId}/assigned")
    public ResponseEntity<Task> assignedTaskToUser(
            @PathVariable Long id,
            @PathVariable Long userId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        if (!StringUtils.hasText(jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        userService.getUserProfileHandler(jwt); // JWT check
        Task task = taskService.assignedToUser(userId, id);
        return ResponseEntity.ok(task);
    }

    /**
     * Update an existing task.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task req,
            @RequestHeader("Authorization") String jwt) throws Exception {

        if (!StringUtils.hasText(jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDto user = userService.getUserProfileHandler(jwt);
        Task task = taskService.updateTask(id, req, user.getId());
        return ResponseEntity.ok(task);
    }

    /**
     * Delete a task.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Mark a task as completed.
     */
    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> completeTask(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {

        if (!StringUtils.hasText(jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Task task = taskService.completeTask(id);
        return ResponseEntity.ok(task);
    }
}
