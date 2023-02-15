package com.accenture.modulosPago.entities;

import com.accenture.modulosPago.dtos.UserDto;
import com.accenture.modulosPago.models.Account;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String name;
    private String lastname;
    @Column (unique = true)
    private String dni;
    @Column (unique = true)
    private String email;
    private String password;
    private Boolean isActive;




    public User() {
    }


    public User(UserDto userDto){
        this.name = userDto.getName();
        this.lastname = userDto.getLastname();
        this.dni = userDto.getDni();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        this.isActive = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
