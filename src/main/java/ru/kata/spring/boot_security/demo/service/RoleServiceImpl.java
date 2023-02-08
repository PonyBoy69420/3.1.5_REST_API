package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleDAO;

import java.util.List;

@Service
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
    public Role getRoleByName(String role) {
        return roleDAO.getRoleByName(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDAO.findAll();
    }
}
