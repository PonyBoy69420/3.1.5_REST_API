package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleDAO;

public class RoleServiceImpl implements RoleService{

    private RoleDAO roleDAO;
    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO){
        this.roleDAO = roleDAO;
    }

    @Override
    public void addRole(Role role) {
        roleDAO.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        roleDAO.deleteById(id);
    }

    @Override
    public void getRoleByName(String role) {
        roleDAO.getRoleByName(role);
    }

    @Override
    public void getAllRoles() {
        roleDAO.findAll();
    }
}
