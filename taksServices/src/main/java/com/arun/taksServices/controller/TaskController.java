package com.arun.taksServices.controller;

import com.arun.taksServices.model.Task;
import com.arun.taksServices.model.TaskStatus;
import com.arun.taksServices.model.UserDto;
import com.arun.taksServices.services.TaskServices;
import com.arun.taksServices.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@CrossOrigin(origins = "*")

public class TaskController {
    @Autowired
    private TaskServices taskServices;
    @Autowired
    private UserServices userServices;
    @PostMapping
    public ResponseEntity<Task>createTask(@RequestBody Task task,
                                          @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto userDto=userServices.getUserProfile(jwt);
        Task createdTask=taskServices.createTask(task,userDto.getRole());
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task>getById(@PathVariable Long id,
                                          @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto userDto=userServices.getUserProfile(jwt);
        Task task=taskServices.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Task>>assignUserTask(@RequestParam(required = false) TaskStatus status,
                                       @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto user=userServices.getUserProfile(jwt);
        List<Task> task=taskServices.assignedUsersTask(user.getId(),status);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<Task>>getAllTask(@RequestParam(required = false) TaskStatus status,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto user=userServices.getUserProfile(jwt);
        List<Task> task=taskServices.getAllTasks(status);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }


    @PutMapping("/{id}/user/{userId}/assigned")
    public ResponseEntity<Task>assignTaskToUser(@PathVariable Long id,@PathVariable Long userId,
                                                @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto user=userServices.getUserProfile(jwt);
        Task task=taskServices.assingnedToUser(userId,id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task>updateTask(@PathVariable Long id, @RequestBody Task req,
                                                @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto user=userServices.getUserProfile(jwt);
        Task task=taskServices.updateTask(id,req,user.getId());
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{id}/complet")
    public ResponseEntity<Task>completTask(@PathVariable Long id) throws Exception {

        Task task=taskServices.completeTask(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteTask(@PathVariable Long id) throws Exception {

        taskServices.deleteTask(id);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }


}
