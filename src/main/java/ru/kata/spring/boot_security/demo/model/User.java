package ru.kata.spring.boot_security.demo.model;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name= "User",schema = "hib")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty(message = "Enter Username")
    @Size(min=2,max=20,message = "Username size must be between 2 and 30")
    @Column(name="username")
    private String username;
    @NotNull
    @NotEmpty(message = "Enter Lastname")
    @Size(min=2,max=20,message = "Lastname size must be between 2 and 30")
    @Column(name="lastName")
    private String lastName;
    @NotNull
    @Email(message = "Not valid email")
    @Column(name = "email")
    private String email;
    @NotNull
    @NotEmpty(message = "Enter your password")
    @Size(min = 6, max=30,message = "password must be between 6 and 30")
    @Column(name="password")
    private String password;

    @NotNull
    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


//    @Enumerated(value=EnumType.STRING)
//    @Column(name="role")
//    private String role;
//    @Enumerated(value=EnumType.STRING)
//    @Column(name="status")
//    private String status;

}
