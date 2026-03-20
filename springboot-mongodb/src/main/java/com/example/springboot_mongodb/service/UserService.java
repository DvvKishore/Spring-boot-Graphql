package com.example.springboot_mongodb.service;

import com.example.springboot_mongodb.model.User;
import com.example.springboot_mongodb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User getById(String id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public List<User> getByName(String name) {
        return repository.findByName(name);
    }


    public List<User> getSortedUsers() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "name"));
    }

    public Page<User> getPaginated(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public Page<User> getUsers(String name, int page, int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.ASC, "name")
        );

        return repository.findByName(name, pageable);
    }
}
