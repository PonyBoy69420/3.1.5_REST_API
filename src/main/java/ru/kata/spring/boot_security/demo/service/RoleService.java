package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

public interface RoleService {
    public void addRole(Role role);
    public void deleteRole(Long id);
    public void getRoleByName(String role);
    public void getAllRoles();
}
