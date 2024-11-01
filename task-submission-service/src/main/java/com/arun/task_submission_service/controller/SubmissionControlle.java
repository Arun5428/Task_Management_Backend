package com.arun.task_submission_service.controller;

import com.arun.task_submission_service.model.Submission;
import com.arun.task_submission_service.model.UserDto;
import com.arun.task_submission_service.services.SubmissionServices;
import com.arun.task_submission_service.services.TaskServices;
import com.arun.task_submission_service.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submission")
@CrossOrigin(origins = "*")
public class SubmissionControlle {
    @Autowired
    private SubmissionServices submissionServices;
    @Autowired
    private UserServices userServices;
    @Autowired
    private TaskServices taskServices;
    @PostMapping

    public ResponseEntity<Submission>submitTask(
            @RequestParam Long taskId,
            @RequestParam String githumLink,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        UserDto user=userServices.getUserProfile(jwt);
        Submission submission=submissionServices.submission(taskId,githumLink,user.getId(),jwt);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")

    public ResponseEntity<Submission>getSubmissionById(
            @PathVariable Long id,
   @RequestHeader("Authorization") String jwt
    ) throws Exception{
        UserDto user=userServices.getUserProfile(jwt);
        Submission submission=submissionServices.getTaskSubmissionById(id);
        return new ResponseEntity<>(submission, HttpStatus.OK);

    }

    @GetMapping

    public ResponseEntity<List<Submission>>getAllSubmission(

            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        UserDto user=userServices.getUserProfile(jwt);
       List< Submission> submission=submissionServices.getAllTaskSubmission();
        return new ResponseEntity<>(submission, HttpStatus.OK);

    }



    @GetMapping("/task/{taskId}")

    public ResponseEntity<List<Submission>>getAllSubissions(
            @PathVariable Long taskId,

            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        UserDto user=userServices.getUserProfile(jwt);
        List<Submission> submission=submissionServices.getTaskSubmissionByTaskId(taskId);
        return new ResponseEntity<>(submission, HttpStatus.OK);

    }
    @PutMapping("/{id}")

    public ResponseEntity<Submission>acceptorDeclineSubmission(
            @PathVariable Long id,
            @RequestParam("status") String status,

            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        UserDto user=userServices.getUserProfile(jwt);
        Submission submission=submissionServices.acceptDeclineSubmission(id,status);
        return new ResponseEntity<>(submission, HttpStatus.OK);

    }



}
