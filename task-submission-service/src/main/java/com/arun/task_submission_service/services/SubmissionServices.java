package com.arun.task_submission_service.services;

import com.arun.task_submission_service.model.Submission;

import java.util.List;

public interface SubmissionServices {
    Submission submission(Long taskId,String githublink,Long userId,String jwt)throws Exception;
    Submission getTaskSubmissionById(Long SubmissionId)throws Exception;
    List<Submission> getAllTaskSubmission();
    List<Submission>getTaskSubmissionByTaskId(Long taskId);
    Submission acceptDeclineSubmission(Long id,String status)throws Exception;



}
