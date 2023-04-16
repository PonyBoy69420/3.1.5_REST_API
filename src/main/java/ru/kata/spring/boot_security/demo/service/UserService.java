package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void createUser(User user);

    void deleteUser(Long id);

    void updateUser(Long id,User user);

    User getUserById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> listUsers();
}

