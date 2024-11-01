package com.example.task.user.services.controller;

import com.example.task.user.services.model.User;
import com.example.task.user.services.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserServices userServices;
    @GetMapping("/profile")
    public ResponseEntity<User>getUserProfile(@RequestHeader("Authorization") String jwt){
        User user=userServices.getByUserProfile(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }


    @GetMapping("/getallusers")
    public ResponseEntity<List<User>>getAllUsers(@RequestHeader("Authorization") String jwt){
        List<User> users=userServices.getAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);

    }
}
