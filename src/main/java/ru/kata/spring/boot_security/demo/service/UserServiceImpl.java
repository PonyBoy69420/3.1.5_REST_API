package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleDAO;
import ru.kata.spring.boot_security.demo.repository.UserDAO;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private UserDAO userDao;
    private RoleDAO roleDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(RoleDAO roleDao,UserDAO userDao, @Lazy PasswordEncoder passwordEncoder){

        this.roleDao=roleDao;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username){
        return userDao.findByUsername(username);
    }


    @Override
    public User findByEmail(String email){return userDao.findByEmail(email);}


    @Override
    @Transactional
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException(String.format("User not found"));
        }
        System.out.println(user.getRoles());
        return user;
    }


    @Override
    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> userRoles = new HashSet<>();
        user.getRoles().stream().forEach(S->{userRoles.add(roleDao.getRoleByName(S.getName()));});
        user.setRoles(userRoles);
        userDao.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public void updateUser(Long id,User user) {
        String newPassword = user.getPassword();
        if(!(getUserById(user.getId()).getPassword().equals(newPassword))){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        Set<Role> userRoles = new HashSet<>();
        user.getRoles().stream().forEach(S->{userRoles.add(roleDao.getRoleByName(S.getName()));});
        user.setRoles(userRoles);
        user.setId(id);
        userDao.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userDao.findById(id).get();
    }

    @Override
    public List<User> listUsers() {
        return userDao.findAll();
    }
}
