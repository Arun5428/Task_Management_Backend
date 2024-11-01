package com.arun.task_submission_service.services;

import com.arun.task_submission_service.model.TaskDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "SUBMISSION-SERVICES",url = "http://localhost:5002/")
public interface TaskServices {

    @GetMapping("/api/task/{id}")
    public TaskDto getTaskById(@PathVariable Long id,
                                           @RequestHeader("Authorization") String jwt) throws Exception;
    @PutMapping("/api/task/{id}/complet")
    public TaskDto completTask(@PathVariable Long id) throws Exception;

}
