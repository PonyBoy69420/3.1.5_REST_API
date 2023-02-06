package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    public void createUser(User user);

    public void deleteUser(Long id);

    public void updateUser(User user);

    public User getUserById(Long id);

    public User findByUsername(String username);

    public List<User> listUsers();
}

