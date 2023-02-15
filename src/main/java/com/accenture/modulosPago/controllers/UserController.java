package com.accenture.modulosPago.controllers;

import com.accenture.modulosPago.Utils;
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
    public ResponseEntity<Object> getById(@PathVariable Long id){
        User user = userService.findById(id);
        if (user==null){
            return new ResponseEntity<>("User no exists, check information", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/createdUser")
    public ResponseEntity<Object> generatedUser(@RequestBody UserDto userDto) {
        try {
            int a = userDto.getDni().length();
            if (!(a > 6 && a < 9)){
                return new ResponseEntity<>("DNI must have 7 or 8 numbers", HttpStatus.NOT_ACCEPTABLE);
            }
            if (!Utils.verifyNumber(userDto.getDni())) {
                return new ResponseEntity<>("Error in DNI field, please check it only numbers.", HttpStatus.NOT_ACCEPTABLE);
            }
            if (userDto.getName().trim().isEmpty() || userDto.getLastname().trim().isEmpty() || userDto.getDni().trim().isEmpty()
                    || userDto.getEmail().trim().isEmpty() || userDto.getPassword().trim().isEmpty()) {
                return new ResponseEntity<>("Missing data, please check all fields", HttpStatus.NOT_ACCEPTABLE);
            }
            if (userService.findByDni(userDto.getDni()) == null) {
                User userNew = userService.generateUser(userDto);
                return new ResponseEntity<>(userNew, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("User ready exit, try other data", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long userId) {
        if (userService.deleteUserById(userId)) {
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User NO deleted because no exist or have account", HttpStatus.NOT_ACCEPTABLE);
        }
    }




    @PostMapping("/updateDataUser")
    public ResponseEntity<Object> updateDataUser(@RequestBody UserDto userDto, @RequestParam Long id){
        try {
            int a = userDto.getDni().length();
            if (!(a > 6 && a < 9)) {
                return new ResponseEntity<>("DNI field must have 7 or 8 digits",HttpStatus.NOT_ACCEPTABLE);
            }
            if (!Utils.verifyNumber(userDto.getDni())) {
                return new ResponseEntity<>("Error in DNI field, please check it only numbers.",HttpStatus.NOT_ACCEPTABLE);
            }
            if (userDto.getName().isEmpty() || userDto.getLastname().isEmpty() || userDto.getDni().isEmpty()
                    || userDto.getEmail().isEmpty() || userDto.getPassword().isEmpty()) {
                return new ResponseEntity<>("Missing data, please check all fields",HttpStatus.NOT_ACCEPTABLE);
            }
            if(userService.findByDni(userDto.getDni())!=null) {
                User userToUpdate = userService.updateDataUser(userDto,id);
                return new ResponseEntity<>(userToUpdate, HttpStatus.OK);
            }else{
                return  new ResponseEntity<>("User NO exits, try other data",HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>("Unexpected error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/listUserAccount/{userId}")
    public ResponseEntity<Object> listUserAccount(@PathVariable Long userId) {
        List<Account> listUserAccount = userService.listUserAccount(userId);
        if (listUserAccount==null) {
            return new ResponseEntity<>("User NO exits, check information", HttpStatus.NOT_ACCEPTABLE);
        }
        if (listUserAccount.isEmpty()) {
            return new ResponseEntity<>("User NO have accounts", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(listUserAccount, HttpStatus.OK);
    }



}



