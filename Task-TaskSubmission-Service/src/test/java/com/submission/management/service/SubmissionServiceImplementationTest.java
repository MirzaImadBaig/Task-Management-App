package com.submission.management.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.MockitoAnnotations;

import com.submission.management.dtos.TaskDto;
import com.submission.management.model.Submission;
import com.submission.management.repository.SubmissionRepository;
import com.submission.management.service.SubmissionServiceImplementation;
import com.submission.management.service.TaskService;

public class SubmissionServiceImplementationTest {

    @Mock
    private SubmissionRepository submissionRepository;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private SubmissionServiceImplementation submissionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSubmitTask_Success() throws Exception {
        TaskDto taskDto = new TaskDto();
        Submission submission = new Submission();
        submission.setTaskId(1L);
        submission.setUserId(2L);
        submission.setGithubLink("https://github.com/user/repo");

        when(taskService.getTaskById(1L, "jwtToken")).thenReturn(taskDto);
        when(submissionRepository.save(any(Submission.class))).thenReturn(submission);

        Submission result = submissionService.submitTask(1L, "https://github.com/user/repo", 2L, "jwtToken");

        assertEquals(1L, result.getTaskId());
        verify(submissionRepository).save(any(Submission.class));
    }

    @Test
    public void testSubmitTask_TaskNotFound() {
        when(taskService.getTaskById(99L, "jwtToken")).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> {
            submissionService.submitTask(99L, "link", 1L, "jwtToken");
        });

        assertTrue(exception.getMessage().contains("Task not found"));
    }

    @Test
    public void testGetTaskSubmissionById_Success() throws Exception {
        Submission submission = new Submission();
        submission.setId(1L);

        when(submissionRepository.findById(1L)).thenReturn(Optional.of(submission));

        Submission result = submissionService.getTaskSubmissionById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    public void testGetTaskSubmissionById_NotFound() {
        when(submissionRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            submissionService.getTaskSubmissionById(99L);
        });

        assertTrue(exception.getMessage().contains("Task submission not found"));
    }

    @Test
    public void testGetAllTaskSubmissions() {
        List<Submission> submissions = Arrays.asList(new Submission(), new Submission());
        when(submissionRepository.findAll()).thenReturn(submissions);

        List<Submission> result = submissionService.getAllTaskSubmissions();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetTaskSubmissionsByTaskId() {
        List<Submission> submissions = Arrays.asList(new Submission());
        when(submissionRepository.findByTaskId(1L)).thenReturn(submissions);

        List<Submission> result = submissionService.getTaskSubmissionsByTaskId(1L);

        assertEquals(1, result.size());
    }

    @Test
    public void testAcceptDeclineSubmission() throws Exception {
        Submission submission = new Submission();
        submission.setId(1L);
        submission.setTaskId(10L);

        when(submissionRepository.findById(1L)).thenReturn(Optional.of(submission));
        when(submissionRepository.save(any(Submission.class))).thenReturn(submission);

        Submission result = submissionService.acceptDeclineSubmission(1L, "COMPLETE");

        assertEquals("COMPLETE", result.getStatus());
        verify(taskService).completeTask(10L);
    }
}
