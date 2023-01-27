package com.accenture.modulosPago.service;

import com.accenture.modulosPago.dtos.UserDto;
import com.accenture.modulosPago.entities.User;
import com.accenture.modulosPago.models.Account;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserServiceInter {

    public List<User> findAll();

    public User findById(Long id);


    public User createdUser(UserDto userDto);
}
