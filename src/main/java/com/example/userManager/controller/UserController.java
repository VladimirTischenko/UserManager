package com.example.userManager.controller;

import com.example.userManager.dao.User;
import com.example.userManager.service.UserService;
import com.example.userManager.to.UserTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UserController.REST_URL)
public class UserController {
    static final String REST_URL = "/users";
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Iterable<UserTo> getAll(@RequestParam(value = "firstName", required = false) String firstName,
                                 @RequestParam(value = "lastName", required = false) String lastName) {
        return service.getAll(firstName, lastName);
    }

    @GetMapping("/{id}")
    public UserTo get(@PathVariable int id) {
        return service.get(id);
    }

    @PostMapping()
    public UserTo addNew(@RequestBody User user) {
        return service.save(user);
    }

    @PutMapping("/{id}")
    public UserTo update(@PathVariable int id, @RequestBody User user) {
        return service.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
