package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserErrorResponse;
import ru.kata.spring.boot_security.demo.util.UserNotCreatedException;
import ru.kata.spring.boot_security.demo.util.UserNotUpdatedException;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin/api")
public class AdminController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public  AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @CrossOrigin
    @PostMapping("users/create-new")
    public ResponseEntity<String> createUser(@RequestBody @Valid User user,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errors = new StringBuilder();
            List<FieldError> errorList = bindingResult.getFieldErrors();
            for(FieldError error : errorList){
                errors.append(error.getField()).append("-").append(error.getDefaultMessage()).append(";");
            }
            throw new UserNotCreatedException(errors.toString());
        }
        userService.createUser(user);
        return new ResponseEntity<>("User was created",HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("users/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>("User was deleted",HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("users/update/{id}")
    public ResponseEntity<String> update(@RequestBody @Valid User user,@PathVariable Long id, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errors = new StringBuilder();
            List<FieldError> errorList = bindingResult.getFieldErrors();
            for(FieldError error : errorList){
                errors.append(error.getField()).append("-").append(error.getDefaultMessage()).append(";");
            }
            throw new UserNotUpdatedException(errors.toString());
        }
        userService.updateUser(id,user);
        return new ResponseEntity<>("User Updated", HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAdminPage(){
        return new ResponseEntity<>(userService.listUsers(),HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getAdminPage(@PathVariable Long id){
        return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getListRoles(){
        return new ResponseEntity<>(roleService.getAllRoles(),HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handlerException(UserNotCreatedException e){
        UserErrorResponse response = new UserErrorResponse(e.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handlerException(UserNotUpdatedException e){
        UserErrorResponse response = new UserErrorResponse(e.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
