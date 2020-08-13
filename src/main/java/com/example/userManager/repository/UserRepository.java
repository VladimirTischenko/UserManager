package com.example.userManager.repository;

import com.example.userManager.dao.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findById(int id);
}
