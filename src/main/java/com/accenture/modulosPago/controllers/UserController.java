package com.accenture.modulosPago.controllers;

import com.accenture.modulosPago.dtos.UserDto;
import com.accenture.modulosPago.entities.User;
import com.accenture.modulosPago.models.Account;
import com.accenture.modulosPago.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> getListUsers(){
        return userService.findAll();
    }

    @GetMapping("/list/{id}")
    public User getById(@PathVariable Long id){
        return userService.findById(id);
    }

    @PostMapping("/createdUser")
    public User createdUser(@RequestBody UserDto userDto){
        return userService.createdUser(userDto);
    }

    @PostMapping("/addAccountToUser")
    public ResponseEntity <Object> addAccountToUser(@RequestBody Account account){
        try{
            Account account1 = userService.addAccountToList(account);
            return new ResponseEntity<>(account1, HttpStatus.CREATED);
        }catch (Exception ex){
            ex.printStackTrace();
            ex.getMessage();
            return new ResponseEntity<>("Unexpected Error" + ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
