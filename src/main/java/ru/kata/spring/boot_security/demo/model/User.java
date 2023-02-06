package ru.kata.spring.boot_security.demo.model;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@Table(name= "User",schema = "hib")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="username")
    private String username;
    @Column(name="lastName")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="role")
    private String role;

    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;


//    @Enumerated(value=EnumType.STRING)
//    @Column(name="role")
//    private String role;
//    @Enumerated(value=EnumType.STRING)
//    @Column(name="status")
//    private String status;

}
