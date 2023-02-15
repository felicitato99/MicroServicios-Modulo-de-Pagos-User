package com.accenture.modulosPago.service;

import com.accenture.modulosPago.dtos.UserDto;
import com.accenture.modulosPago.entities.User;
import com.accenture.modulosPago.models.Account;
import com.accenture.modulosPago.repositories.UserRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.util.*;
import java.util.stream.Collectors;

@Service("userServiceRestTemplate")
public class UserService implements UserServiceInter {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate clientRest;

    @Autowired
    JmsTemplate jmsTemplate;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Transactional(readOnly = true) //spring
    public List<User> findAll() {
        logger.info("MSUsers RestTemplated: find list all User");
        return (List<User>) userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        logger.info("MSUsers RestTemplated: find User from idUser");
        return userRepository.findById(id).orElse(null);
    }

    public User generateUser(UserDto userDto) {
        User user = new User(userDto);
        User user1 = userRepository.save(user);
        System.out.println(user1);
        clientRest.postForEntity("http://localhost:8002/api/account/createdAccount/", user, Account.class);
        Map<String, String> map = new HashMap<>();
        map.put("name", user.getName());
        map.put("lastname", user.getLastname());
        map.put("dni", user.getDni());
        map.put("email", user.getEmail());
        JSONObject jo = new JSONObject(map);

        jmsTemplate.send("queue.created.user", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage object = session.createObjectMessage(jo.toString());
                return object;
            }
        });
        logger.info("MSUsers RestTemplated: created User");
        return user;
    }


    public List<Account> listUserAccount(Long userId) {
        logger.info("MSUsers RestTemplated: show if User have accounts");
        Optional<User> userLookinFor = userRepository.findById(userId);
        if (userLookinFor.isPresent()) {
            Map<String, String> pathVariables = new HashMap<String, String>();
            pathVariables.put("userId", userId.toString());
            List<Account> accountListExist = Arrays.stream(clientRest.getForObject("http://localhost:8002/api/account/listAccount/{userId}", Account[].class, pathVariables)).toList();
            if (!accountListExist.isEmpty()) {
                return accountListExist;
            } else {
                return accountListExist;
            }
        } else {
            return null;
        }
    }

    public Boolean deleteUserById(Long userId) {
        logger.info("MSUsers RestTemplated: Try deleted User");
        Optional<User> userToDelete = userRepository.findById(userId);
        if (userToDelete.isPresent()) {
            Map<String, String> pathVariables = new HashMap<String, String>();
            pathVariables.put("idUser", userId.toString());
            List<Account> accountListExist = Arrays.stream(clientRest.getForObject("http://localhost:8002/api/account/listAccount/{userId}", Account[].class, pathVariables)).toList();
            if (accountListExist.isEmpty()) {
                userRepository.delete(userToDelete.get());
                return true;
            }
        }
        return false;
    }

    public User updateDataUser(UserDto userDto, Long id) {
        Optional<User> userToUpdate = userRepository.findById(id);
        if (userToUpdate.isPresent()) {
            userToUpdate.get().setDni(userDto.getDni());
            userToUpdate.get().setEmail(userDto.getEmail());
            userToUpdate.get().setName(userDto.getName());
            userToUpdate.get().setLastname(userDto.getLastname());
            userToUpdate.get().setPassword(userDto.getPassword());
            return userRepository.save(userToUpdate.get());
        } else {
            return null;
        }
    }

    public User findByDni(String dni) {
        return userRepository.findByDni(dni);
    }

}

