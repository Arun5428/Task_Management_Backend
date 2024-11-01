package com.example.task.user.services.services;

import com.example.task.user.services.config.JwtProvider;
import com.example.task.user.services.model.User;
import com.example.task.user.services.repo.UserRepository;
import org.hibernate.engine.jdbc.spi.JdbcWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicesImp implements UserServices{
    @Autowired
    private UserRepository userRepository;
    @Override
    public User getByUserProfile(String jwt) {
       String email=JwtProvider.getEmailformjwtToken(jwt);
       return userRepository.findByEmail(email);

    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
