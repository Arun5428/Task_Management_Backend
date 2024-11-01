package com.arun.taksServices.controller;

import com.arun.taksServices.model.Task;
import com.arun.taksServices.model.TaskStatus;
import com.arun.taksServices.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class HomeController {

    @GetMapping("/task")
    public ResponseEntity<String> assignUserTask() throws Exception {

        return new ResponseEntity<>("welcome to task service", HttpStatus.OK);
    }

}
