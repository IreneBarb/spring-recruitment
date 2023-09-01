package com.unipi.backend.service;

import java.util.List;

import com.unipi.backend.model.User;
import com.unipi.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService() {
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User loginUser(String username, String password) {
        User user = this.userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password) ? user : null;
    }
}
