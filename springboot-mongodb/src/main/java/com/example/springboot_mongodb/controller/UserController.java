package com.example.springboot_mongodb.controller;

import com.example.springboot_mongodb.model.User;
import com.example.springboot_mongodb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public User create(@RequestBody User user) {
        return service.save(user);
    }

    @GetMapping
    public List<User> getAll() {
        return service.getAll();
    }

    @GetMapping("get/{id}")
    public User getById(@PathVariable String id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
    @GetMapping("/{name}")
    public List<User> getByName(@PathVariable String name) {
        return service.getByName(name);
    }

    @GetMapping("/sorted")
    public List<User> getSorted() {
        return service.getSortedUsers();
    }

    @GetMapping("/page")
    public Page<User> getPage(
            @RequestParam int page,
            @RequestParam int size) {
        return service.getPaginated(page, size);
    }

    @GetMapping("/search")
    public Page<User> search(
            @RequestParam String name,
            @RequestParam int page,
            @RequestParam int size) {

        return service.getUsers(name, page, size);
    }
}
