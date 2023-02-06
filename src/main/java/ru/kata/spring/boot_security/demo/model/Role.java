package ru.kata.spring.boot_security.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @ManyToMany
    @JoinTable(name="users_roles", joinColumns= @JoinColumn(name="role_id")
    ,inverseJoinColumns= @JoinColumn(name="user_id"))
    private List<User> users;
}
