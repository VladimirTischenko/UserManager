package com.example.userManager.service;

import com.example.userManager.dao.User;
import com.example.userManager.to.UserTo;

public interface UserService {
    Iterable<UserTo> getAll(String firstName, String lastName);
    UserTo get(int id);
    UserTo save(User user);
    UserTo update(int id, User user);
    void delete(int id);
}
