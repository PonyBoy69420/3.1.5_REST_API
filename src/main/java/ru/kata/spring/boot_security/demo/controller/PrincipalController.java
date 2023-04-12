package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@RestController
public class PrincipalController {

    private UserService userService;

    @Autowired
    public PrincipalController(UserService userService){
        this.userService=userService;
    }

    @CrossOrigin
    @GetMapping("api/principal")
    public ResponseEntity<User> getPrincipal(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(userService.findByEmail(user.getEmail()), HttpStatus.OK);
    }
}
