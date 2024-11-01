package com.example.task.user.services.services;

import com.example.task.user.services.model.User;

import java.util.List;

public interface UserServices {
    public User getByUserProfile(String jwt);
    public List<User>getAllUser();
}
