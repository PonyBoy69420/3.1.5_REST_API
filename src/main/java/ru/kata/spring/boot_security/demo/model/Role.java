package ru.kata.spring.boot_security.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role(){}

    public Role(Long id,String name){
        this.id=id;
        this.name=name;
    }

    public Role(String name){
        this.name=name;
    }

    @Override
    @JsonIgnore
    public String getAuthority() {
        return getName();
    }

    public String getNameWithoutPrefix(){
        return name.replace("ROLE_","");
    }
}
