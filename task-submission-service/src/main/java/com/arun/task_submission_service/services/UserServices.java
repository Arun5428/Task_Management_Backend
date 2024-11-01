package com.arun.task_submission_service.services;

import com.arun.task_submission_service.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="USER-SERVICES",url = "http://localhost:5001/")
public interface UserServices {
    @GetMapping("api/user/profile")
    public UserDto getUserProfile(@RequestHeader("Authorization") String jwt);

}
