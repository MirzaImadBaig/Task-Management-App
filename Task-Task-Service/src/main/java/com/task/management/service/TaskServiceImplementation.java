package com.task.management.service;

import org.springframework.stereotype.Service;
import com.task.management.enums.TaskStatus;
import com.task.management.model.Task;
import com.task.management.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImplementation implements TaskService {

    private final TaskRepository taskRepository;

    // Constructor-based dependency injection
    public TaskServiceImplementation(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Creates a new task if the requester has ADMIN privileges.
     */
    @Override
    public Task createTask(Task task, String requesterRole) throws Exception {
        if (!"ROLE_ADMIN".equalsIgnoreCase(requesterRole)) {
            throw new Exception("Access denied: Only admins can create tasks.");
        }

        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    /**
     * Fetch a task by ID or throw exception if not found.
     */
    @Override
    public Task getTaskById(Long id) throws Exception {
        return taskRepository.findById(id)
                .orElseThrow(() -> new Exception("Task not found with ID: " + id));
    }

    /**
     * Returns all tasks filtered by status and sorted by deadline or createdAt.
     */
    @Override
    public List<Task> getAllTasks(TaskStatus status, String sortByDeadline, String sortByCreatedAt) {
        List<Task> allTasks = taskRepository.findAll();

        // Filter by status if provided
        List<Task> filteredTasks = allTasks.stream()
                .filter(task -> status == null || task.getStatus() == status)
                .collect(Collectors.toList());

        // Sort by deadline or createdAt if specified
        if (sortByDeadline != null && !sortByDeadline.isEmpty()) {
            filteredTasks.sort(Comparator.comparing(Task::getDeadline));
        } else if (sortByCreatedAt != null && !sortByCreatedAt.isEmpty()) {
            filteredTasks.sort(Comparator.comparing(Task::getCreatedAt));
        }

        return filteredTasks;
    }

    /**
     * Updates allowed fields of a task if not null in the update request.
     */
    @Override
    public Task updateTask(Long id, Task updatedTask, Long userId) throws Exception {
        Task existingTask = getTaskById(id);

        if (updatedTask.getTitle() != null) {
            existingTask.setTitle(updatedTask.getTitle());
        }
        if (updatedTask.getImage() != null) {
            existingTask.setImage(updatedTask.getImage());
        }
        if (updatedTask.getDescription() != null) {
            existingTask.setDescription(updatedTask.getDescription());
        }
        if (updatedTask.getStatus() != null) {
            existingTask.setStatus(updatedTask.getStatus());
        }
        if (updatedTask.getDeadline() != null) {
            existingTask.setDeadline(updatedTask.getDeadline());
        }

        return taskRepository.save(existingTask);
    }

    /**
     * Deletes a task by its ID.
     */
    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    /**
     * Assigns a task to a user and marks its status as ASSIGNED.
     */
    @Override
    public Task assignedToUser(Long userId, Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setAssignedUserId(userId);
        task.setStatus(TaskStatus.ASSIGNED);

        return taskRepository.save(task);
    }

    /**
     * Returns all tasks assigned to a specific user, with optional filtering and sorting.
     */
    @Override
    public List<Task> assignedUsersTask(Long userId, TaskStatus status, String sortByDeadline, String sortByCreatedAt) {
        List<Task> userTasks = taskRepository.findByassignedUserId(userId);

        List<Task> filteredTasks = userTasks.stream()
                .filter(task -> status == null || task.getStatus() == status)
                .collect(Collectors.toList());

        if (sortByDeadline != null && !sortByDeadline.isEmpty()) {
            filteredTasks.sort(Comparator.comparing(Task::getDeadline));
        } else if (sortByCreatedAt != null && !sortByCreatedAt.isEmpty()) {
            filteredTasks.sort(Comparator.comparing(Task::getCreatedAt));
        }

        return filteredTasks;
    }

    /**
     * Marks a task as DONE.
     */
    @Override
    public Task completeTask(Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }
}
