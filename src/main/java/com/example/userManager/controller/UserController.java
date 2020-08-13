package com.example.userManager.controller;

import com.example.userManager.dao.User;
import com.example.userManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(path="/all")
    public Iterable<User> getAllUsers() {
        return service.getAll();
    }
}
