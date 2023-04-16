package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.EmailDuplicateException;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public  AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }



    @PostMapping("users")
    public ResponseEntity<String> createUser(@RequestBody @Valid User user){
        try{
            userService.createUser(user);
        }catch (Exception e){
            throw new EmailDuplicateException(e.getMessage());
        }
        return new ResponseEntity<>("User was created",HttpStatus.OK);
    }


    @DeleteMapping("users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>("User was deleted",HttpStatus.OK);
    }


    @PutMapping("users/{id}")
    public ResponseEntity<String> update(@RequestBody @Valid User user,@PathVariable Long id){
        try{
            userService.updateUser(id,user);
        }catch (Exception e){
            throw new EmailDuplicateException(e.getMessage());
    }
        return new ResponseEntity<>("User Updated", HttpStatus.OK);
    }


    @GetMapping("users")
    public ResponseEntity<List<User>> getAdminPage(){
        return new ResponseEntity<>(userService.listUsers(),HttpStatus.OK);
    }


    @GetMapping("users/{id}")
    public ResponseEntity<User> getAdminPage(@PathVariable Long id){
        return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);
    }


}
