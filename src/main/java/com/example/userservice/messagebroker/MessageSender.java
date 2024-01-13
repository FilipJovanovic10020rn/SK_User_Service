package com.example.userservice.messagebroker;

import com.example.userservice.dtos.NotifyUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

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
    public void sendMessage(String destination, NotifyUserDto notifyUserDto) {
        jmsTemplate.convertAndSend(destination, notifyUserDto);
    }
}