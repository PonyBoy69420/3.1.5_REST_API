package ru.kata.spring.boot_security.demo.model;


import lombok.Data;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Range;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.repository.RoleDAO;
import ru.kata.spring.boot_security.demo.repository.UserDAO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name= "User",schema = "hib")
public class User implements UserDetails{


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
    @Size(min=2,message = "Enter your email")
    @Column(name = "email",unique=true)
    private String email;
    @NotNull
    @NotEmpty(message = "Enter your password")
    @Size(min = 6, max=30,message = "password must be between 6 and 30")
    @Column(name="password")
    private String password;
    @NotNull
    @Range(min=5,max=120,message = "Age must be between 0 and 120")
    @Column(name="age")
    private int age;

    @NotNull
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;



    public User(String email,String password,Set<Role> roles){
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User() {

    }

    @Transactional
    public Set<Role> getThisRoles(){
        return this.roles;
    }

    public User(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }

    public String getRolesName(){
        List<String> rolesName = new ArrayList<>();
        this.getRoles().forEach(role -> rolesName.add(role.getName()));
        return rolesName.toString().replace("[","").replace("]","");
    }


    @Override
    public Set<Role> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



//    @Enumerated(value=EnumType.STRING)
//    @Column(name="role")
//    private String role;
//    @Enumerated(value=EnumType.STRING)
//    @Column(name="status")
//    private String status;

}
