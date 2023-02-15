package com.accenture.modulosPago.activeMQ;

import com.accenture.modulosPago.dtos.UserDto;
import com.accenture.modulosPago.entities.User;
import com.accenture.modulosPago.repositories.UserRepository;
import com.accenture.modulosPago.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Receiver {
    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(Receiver.class);

    @JmsListener(destination = "queue.created.user")
    public void receiveMessage(String message){
        try{
            ObjectMapper mapper = new ObjectMapper();
            UserDto user = mapper.readValue(message, UserDto.class);
            user.setActive(true);
            logger.info("Sent email  " + user);
        } catch (IOException e){
            System.out.println(e);
        }
        catch (Exception f){
            System.out.println(f);
            throw f;
        }
    }
}
