package com.accenture.modulosPago.service;

import com.accenture.modulosPago.dtos.UserDto;
import com.accenture.modulosPago.entities.User;
import com.accenture.modulosPago.models.Account;
import com.accenture.modulosPago.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import java.util.*;
import java.util.stream.Collectors;

@Service ("userServiceRestTemplate")
public class UserService implements UserServiceInter {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate clientRest;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    public User createdUser(UserDto userDto) {
        User userAux = new User(userDto);
        userRepository.save(userAux);
        User user = userRepository.findByDni(userAux.getDni());
        clientRest.postForEntity("http://localhost:8002/api/account/createdAccount/", user, Account.class);
        Map<String, String> pathVariables = new HashMap<String, String>();
        pathVariables.put("userId", user.getId().toString());
        Set<Account> accountList = Arrays.stream(clientRest.getForObject("http://localhost:8002/api/account/listAccountCreated/{userId}", Account[].class, pathVariables)).collect(Collectors.toSet());
        user.setAccountList(accountList);
        Set<Long> idAccountList = new HashSet<>();
        for (Account aux : accountList) {
            idAccountList.add(aux.getId());
        }
        user.setAccountIdList(idAccountList);
        userRepository.save(user);
        return user;
    }

    public Account addAccountToList(Account account) {
        Optional<User> userUpdate = userRepository.findById(account.getUserId());
        if (userUpdate.isPresent()) {
            Set<Long> accountsAdd = new HashSet<>();
            accountsAdd.addAll(userUpdate.get().getAccountIdList());
            accountsAdd.add(account.getId());
            userUpdate.get().setAccountIdList(accountsAdd);
            userRepository.save(userUpdate.get());
        }
        return account;
    }
}
