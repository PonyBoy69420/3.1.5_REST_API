package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleDAO;
import ru.kata.spring.boot_security.demo.repository.UserDAO;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException(String.format("User not found"));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),
            mapRolesAuthorities(user.getRoles()));
    }



    private Collection<? extends GrantedAuthority> mapRolesAuthorities(Collection<Role> roles){
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    @Override
    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Role> userRoles = new ArrayList<>();
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Role> userRoles = new ArrayList<>();
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
