package com.example.userservice.messagebroker;

import com.example.userservice.dtos.AddMenagerRoomDto;
import com.example.userservice.model.User;
import com.example.userservice.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    // todo vanja pogledaj
    @JmsListener(destination = "user-service/addWorkplace")
    public void receiveMessageVerify(String addMenagerRoomDtoJson) throws JsonProcessingException {
        // Process the received message
        System.out.println("Setujem sobu za : " + addMenagerRoomDtoJson);

        ObjectMapper objectMapper = new ObjectMapper();
        AddMenagerRoomDto addMenagerRoomDto = objectMapper.readValue(addMenagerRoomDtoJson, AddMenagerRoomDto.class);

        Optional<User> user = userRepository.findById(addMenagerRoomDto.getManagerId());
        if(user.isPresent()){
            user.get().setWorkout_room_name(addMenagerRoomDto.getName());
            userRepository.save(user.get());
        }
    }

    @JmsListener(destination = "user-service/book")
    public void receiveMessageAddWorkout(Long id) {
        // Process the received message
        System.out.println("dodajem workout za: " + id);

        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            user.get().setWorkout_count(user.get().getWorkout_count()+1);
            userRepository.save(user.get());

        }


    }

    @JmsListener(destination = "user-service/cancel")
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