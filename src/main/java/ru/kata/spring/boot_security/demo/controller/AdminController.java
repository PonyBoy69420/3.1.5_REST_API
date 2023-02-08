package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public  AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String listUsers(Model model){
        model.addAttribute("listusers",userService.listUsers());
        return "/listusers";
    }

    @PostMapping("users/create-new")
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult,@RequestParam(value = "roles") List<Role> roles){
        user.setRoles(roles);
        if(bindingResult.hasErrors())
            return "new";
        userService.createUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("users/new")
    public String newUser(@ModelAttribute("user") User user,Model model){
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("allRoles",roles);
        return "/new";
    }

    @DeleteMapping("users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("users/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model){
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("allRoles",roles);
        model.addAttribute("user",userService.getUserById(id));
        return "edit";
    }

    @PatchMapping("users/{id}/edit-user")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @RequestParam(value = "roles") List<Role> roles){
        if(bindingResult.hasErrors())
            return "edit";
        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("users/{id}")
    public String getUserById(@PathVariable("id") Long id, Model model){
        model.addAttribute("user", userService.getUserById(id));
        return "/user-info";
    }
}
