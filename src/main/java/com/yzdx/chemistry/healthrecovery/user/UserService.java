package com.yzdx.chemistry.healthrecovery.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User registerUser(User user) {
        if (getUser(user.getUserId()) == null) {
            user.setCreatedDate(new Date());
            user.setPassword(user.getPassword());
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void uploadPhoto(String userId, String photo) {
        userRepository.updatePhoto(userId, photo);
    }

    public User getUser(String userId) {
        return userRepository.getOne(userId);
    }

    public boolean verifyPassword(String userId, String password) {
        return userRepository.findByUserIdAndPassword(userId, password) != null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}