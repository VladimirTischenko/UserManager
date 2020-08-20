package com.example.userManager.controller;

import com.example.userManager.dao.User;
import com.example.userManager.service.UserService;
import com.example.userManager.to.UserTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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
    public ResponseEntity<Object> create(@Valid @RequestBody User user) {
        Integer id = user.getId();
        if (id != null) {
            return ResponseEntity.badRequest().body("id must be new (id = " + id + ")");
        }

        UserTo userTo = service.save(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}").buildAndExpand(userTo.getId()).toUri();

        return ResponseEntity.created(uri).body(userTo);
    }

    @PutMapping("/{id}")
    public UserTo update(@PathVariable int id, @Valid @RequestBody User user) {
        return service.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
