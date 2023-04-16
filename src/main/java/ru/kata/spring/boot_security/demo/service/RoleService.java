package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    void addRole(Role role);
    void deleteRole(Long id);
    Role getRoleByName(String role);
    List<Role> getAllRoles();
}
