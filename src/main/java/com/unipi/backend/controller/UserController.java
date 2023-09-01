package com.unipi.backend.controller;

import java.util.List;

import com.unipi.backend.model.Credentials;
import com.unipi.backend.model.User;
import com.unipi.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/"})
public class UserController {
    @Autowired
    private UserService userService;

    public UserController() {
    }

    @CrossOrigin(
            origins = {"http://localhost:4200"}
    )
    @GetMapping({"/users"})
    public List<User> getAllUsers() {
        return this.userService.findAll();
    }

    @CrossOrigin(
            origins = {"http://localhost:4200"}
    )
    @PostMapping({"/users/login"})
    public ResponseEntity<User> loginUser(@RequestBody Credentials credentials) {
        User user = this.userService.loginUser(credentials.getUsername(), credentials.getPassword());
        return ResponseEntity.ok(user);
    }
}
