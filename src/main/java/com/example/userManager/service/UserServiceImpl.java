package com.example.userManager.service;

import com.example.userManager.dao.User;
import com.example.userManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User get(int id) {
        return repository.findById(id);
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void delete(int id) {
    }
}
