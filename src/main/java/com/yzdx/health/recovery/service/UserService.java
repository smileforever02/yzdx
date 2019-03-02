package com.yzdx.health.recovery.service;

import com.yzdx.health.recovery.entity.user.User;
import com.yzdx.health.recovery.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User registerUser(User user) {
        if (!getUser(user.getUserId()).isPresent()) {
            user.setCreatedDate(new Date());
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUser(String userId) {
        return userRepository.findById(userId);
    }

    public boolean verifyPassword(String userId, String password) {
        return userRepository.findByUserIdAndPassword(userId, password) != null;
    }
}