package com.example.userManager.service;

import com.example.userManager.dao.User;
import com.example.userManager.to.UserDTO;

public interface UserService {
    Iterable<UserDTO> getAll(String firstName, String lastName);
    UserDTO get(int id);
    UserDTO save(User user);
    UserDTO update(int id, User user);
    void delete(int id);
}
