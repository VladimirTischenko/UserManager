package com.example.userManager.service;

import com.example.userManager.dao.User;
import com.example.userManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<User> getAll(String firstName, String lastName) {
        if (firstName == null) {
            if (lastName == null) {
                return repository.findAll();
            } else {
                return repository.findByLastName(lastName);
            }
        } else if (lastName == null) {
            return repository.findByFirstName(firstName);
        } else {
            return repository.findByFirstNameAndLastName(firstName, lastName);
        }
    }

    @Override
    public User get(int id) {
        return repository.findById(id);
    }

    @Override
    public User save(User user) {
        user.setCreated(new Date());
        return repository.save(user);
    }

    @Override
    public User update(int id, User updatedUser) {
        User userFromDb = repository.findById(id);
        userFromDb.setFirstName(updatedUser.getFirstName());
        userFromDb.setLastName(updatedUser.getLastName());
        userFromDb.setPassword(updatedUser.getPassword());
        userFromDb.setActive(updatedUser.isActive());
        userFromDb.setUpdated(new Date());
        return repository.save(userFromDb);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}
