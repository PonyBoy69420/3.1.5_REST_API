package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    public void addRole(Role role);
    public void deleteRole(Long id);
    public Role getRoleByName(String role);
    public List<Role> getAllRoles();
}
