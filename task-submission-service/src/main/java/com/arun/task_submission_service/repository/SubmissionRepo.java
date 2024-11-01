package com.arun.task_submission_service.repository;

import com.arun.task_submission_service.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepo extends JpaRepository<Submission,Long> {
    List<Submission>findByTaskId(Long taskId);

}