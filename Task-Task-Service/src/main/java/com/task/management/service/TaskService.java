package com.task.management.service;



import java.util.List;

import com.task.management.enums.TaskStatus;
import com.task.management.model.Task;


public interface TaskService {


    Task createTask(Task task, String requesterRole) throws Exception;

    Task getTaskById(Long id) throws Exception;

    List<Task> getAllTasks(TaskStatus status, String sortByDeadline, String sortByCreatedAt);

    Task updateTask(Long id, Task updatedTask, Long userId) throws Exception;

    void deleteTask(Long id);

    Task assignedToUser(Long userId,Long taksId) throws Exception;

    List<Task> assignedUsersTask(Long userId,TaskStatus status, String sortByDeadline, String sortByCreatedAt);

    Task completeTask(Long taskId) throws Exception;

}
