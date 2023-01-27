package com.accenture.modulosPago.dtos;

import com.accenture.modulosPago.entities.User;

public class UserDto {
    private String name;
    private String lastname;
    private String dni;
    private String email;
    private String password;

    public UserDto(User user) {
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.dni = user.getDni();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
    public UserDto(String name, String lastname, String dni, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.email = email;
        this.password = password;
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
}
