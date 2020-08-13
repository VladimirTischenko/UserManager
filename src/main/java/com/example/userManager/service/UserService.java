package com.example.userManager.service;

import com.example.userManager.dao.User;

public interface UserService {
    Iterable<User> getAll();
}
