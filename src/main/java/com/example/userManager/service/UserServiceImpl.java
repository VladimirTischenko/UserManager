package com.example.userManager.service;

import com.example.userManager.dao.User;
import com.example.userManager.repository.UserRepository;
import com.example.userManager.to.UserDTO;
import com.example.userManager.util.PasswordUtil;
import com.example.userManager.util.UserUtil;
import com.example.userManager.util.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    private static final String NOT_FOUND_USER_WITH_ID_MESSAGE = "Not found user with id = ";
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<UserDTO> getAll(String firstName, String lastName) {
        Iterable<User> users;

        if (firstName == null) {
            if (lastName == null) {
                users = repository.findAll();
            } else {
                users = repository.findByLastName(lastName);
            }
        } else if (lastName == null) {
            users = repository.findByFirstName(firstName);
        } else {
            users = repository.findByFirstNameAndLastName(firstName, lastName);
        }

        return UserUtil.convertCollectionToTo(users);
    }

    @Override
    public UserDTO get(int id) {
        User user = findById(id);
        return UserUtil.convertToTo(user);
    }

    @Override
    public UserDTO save(User user) {
        user.setCreated(nowWithNano0());
        user.setPassword(PasswordUtil.crypt(user.getPassword()));
        user = repository.save(user);
        return UserUtil.convertToTo(user);
    }

    @Override
    public UserDTO update(int id, User updatedUser) {
        User userFromDb = findById(id);

        userFromDb.setFirstName(updatedUser.getFirstName());
        userFromDb.setLastName(updatedUser.getLastName());
        userFromDb.setPassword(PasswordUtil.crypt(updatedUser.getPassword()));
        userFromDb.setActive(updatedUser.isActive());
        userFromDb.setUpdated(nowWithNano0());

        repository.save(userFromDb);
        return UserUtil.convertToTo(userFromDb);
    }

    @Override
    public void delete(int id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(NOT_FOUND_USER_WITH_ID_MESSAGE + id);
        }
    }

    private User findById(int id) {
        User user = repository.findById(id);

        if (user == null) {
            throw new UserNotFoundException(NOT_FOUND_USER_WITH_ID_MESSAGE + id);
        }

        return user;
    }

    private LocalDateTime nowWithNano0() {
        return LocalDateTime.now().withNano(0);
    }
}
