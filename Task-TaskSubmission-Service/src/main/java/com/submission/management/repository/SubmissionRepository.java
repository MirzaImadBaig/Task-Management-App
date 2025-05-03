package com.submission.management.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.submission.management.model.Submission;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission,Long> {
    List<Submission> findByTaskId(Long taskId);
}
