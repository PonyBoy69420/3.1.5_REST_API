package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;

    @Autowired
    public  AdminController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public String listUsers(Model model){
        model.addAttribute("listusers",userService.listUsers());
        return "/listusers";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "new";
        userService.createUser(user);
        return "redirect:/users";
    }

    @GetMapping("users/new")
    public String newUser(@ModelAttribute("user") User user){
        return "/new";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("users/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model){
        model.addAttribute("user",userService.getUserById(id));
        return "edit";
    }

    @PatchMapping("users/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "edit";
        userService.updateUser(user);
        return "redirect:/users";
    }

    @GetMapping("users/{id}")
    public String getUserById(@PathVariable("id") Long id, Model model){
        model.addAttribute("user", userService.getUserById(id));
        return "/user-info";
    }
}
