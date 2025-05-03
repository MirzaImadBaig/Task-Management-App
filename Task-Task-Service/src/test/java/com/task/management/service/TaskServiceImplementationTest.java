package com.task.management.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.task.management.enums.TaskStatus;
import com.task.management.model.Task;
import com.task.management.repository.TaskRepository;
import com.task.management.service.TaskServiceImplementation;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceImplementationTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImplementation taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask_AsAdmin() throws Exception {
        Task task = new Task();
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.createTask(task, "ADMIN");

        assertNotNull(result);
        verify(taskRepository).save(task);
    }

    @Test
    void testCreateTask_AsNonAdmin_ThrowsException() {
        Task task = new Task();
        Exception ex = assertThrows(Exception.class, () -> {
            taskService.createTask(task, "USER");
        });

        assertEquals("Only admins can create tasks.", ex.getMessage());
    }

    @Test
    void testGetTaskById_Success() throws Exception {
        Task task = new Task();
        task.setId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById(2L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class, () -> {
            taskService.getTaskById(2L);
        });

        assertTrue(ex.getMessage().contains("task not found with id"));
    }

    @Test
    void testGetAllTasks_FilterAndSort() {
        Task t1 = new Task(); t1.setStatus(TaskStatus.PENDING); t1.setCreatedAt(LocalDateTime.now().minusDays(1));
        Task t2 = new Task(); t2.setStatus(TaskStatus.PENDING); t2.setCreatedAt(LocalDateTime.now());
        when(taskRepository.findAll()).thenReturn(Arrays.asList(t2, t1));

        List<Task> tasks = taskService.getAllTasks(TaskStatus.PENDING, null, "asc");

        assertEquals(2, tasks.size());
        assertTrue(tasks.get(0).getCreatedAt().isBefore(tasks.get(1).getCreatedAt()));
    }

    @Test
    void testUpdateTask_OnlyUpdatesNonNullFields() throws Exception {
        Task existing = new Task(); existing.setId(1L); existing.setTitle("Old Title");
        Task update = new Task(); update.setTitle("New Title");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(taskRepository.save(any(Task.class))).thenReturn(existing);

        Task result = taskService.updateTask(1L, update, 10L);
        assertEquals("New Title", result.getTitle());
    }

    @Test
    void testDeleteTask_CallsRepository() {
        taskService.deleteTask(3L);
        verify(taskRepository).deleteById(3L);
    }

    @Test
    void testAssignedToUser_UpdatesAndSaves() throws Exception {
        Task task = new Task(); task.setId(4L);
        when(taskRepository.findById(4L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.assignedToUser(100L, 4L);

        assertEquals(TaskStatus.ASSIGNED, result.getStatus());
        assertEquals(100L, result.getAssignedUserId());
    }

    @Test
    void testAssignedUsersTask_FilterAndSort() {
        Task t1 = new Task(); t1.setStatus(TaskStatus.ASSIGNED); t1.setDeadline(LocalDateTime.now().plusDays(2));
        Task t2 = new Task(); t2.setStatus(TaskStatus.ASSIGNED); t2.setDeadline(LocalDateTime.now().plusDays(1));
        when(taskRepository.findByassignedUserId(10L)).thenReturn(Arrays.asList(t1, t2));

        List<Task> tasks = taskService.assignedUsersTask(10L, TaskStatus.ASSIGNED, "asc", null);

        assertEquals(2, tasks.size());
        assertTrue(tasks.get(0).getDeadline().isBefore(tasks.get(1).getDeadline()));
    }

    @Test
    void testCompleteTask_UpdatesStatus() throws Exception {
        Task task = new Task(); task.setId(6L); task.setStatus(TaskStatus.ASSIGNED);
        when(taskRepository.findById(6L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.completeTask(6L);

        assertEquals(TaskStatus.DONE, result.getStatus());
    }
}
