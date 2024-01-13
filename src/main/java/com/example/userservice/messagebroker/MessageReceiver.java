package com.example.userservice.messagebroker;

import com.example.userservice.model.User;
import com.example.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Receiver poruka od drugih servisa
@Service
public class MessageReceiver {

    private final UserRepository userRepository;

    @Autowired
    public MessageReceiver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @JmsListener(destination = "user-service/verify")
    public void receiveMessageVerify(Long id) {
        // Process the received message
        System.out.println("Verifikovan email za: " + id);

        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            user.get().setVerified(true);
            userRepository.save(user.get());
        }
    }

    @JmsListener(destination = "user-service/addWorkout")
    public void receiveMessageAddWorkout(Long id) {
        // Process the received message
        System.out.println("dodajem workout za: " + id);

        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            user.get().setWorkout_count(user.get().getWorkout_count()+1);
            userRepository.save(user.get());
        }
    }

    @JmsListener(destination = "user-service/removeWorkout")
    public void receiveMessageRemoveWorkout(Long id) {
        // Process the received message
        System.out.println("oduzimam workout za: " + id);

        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            if(user.get().getWorkout_count()-1 >= 0){
                user.get().setWorkout_count(user.get().getWorkout_count()-1);
                userRepository.save(user.get());
            }
        }
    }
}