package com.example.userservice.messagebroker;

import com.example.userservice.dtos.NotifyUserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

// Za slanje poruka
@Service
public class MessageSender {
    @Autowired
    private JmsTemplate jmsTemplate;

    // ovo ne treba jer nigde ne saljem obican msg
//    public void sendMessage(String destination, String message) {
//        jmsTemplate.convertAndSend(destination, message);
//    }

    // Ovo je sve za notifikaciju samo ce se menjati destination tamo
    public void sendMessage(String destination, NotifyUserDto notifyUserDto) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(notifyUserDto);

        jmsTemplate.convertAndSend(destination, jsonString);
    }

    public void sendMessage(String destination, String encodedKey) {
        jmsTemplate.convertAndSend(destination,encodedKey);
    }
}