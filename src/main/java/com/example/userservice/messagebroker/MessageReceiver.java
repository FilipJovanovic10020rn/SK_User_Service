package com.example.userservice.messagebroker;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

// Receiver component in Application 2
@Component
public class MessageReceiver {
    @JmsListener(destination = "user-service")
    public void receiveMessage(String message) {
        // Process the received message
        System.out.println("Received message: " + message);
    }
}