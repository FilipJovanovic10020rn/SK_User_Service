package com.example.userservice.services;

import com.example.userservice.dtos.EditUserDto;
import com.example.userservice.dtos.CreateUserDto;
import com.example.userservice.model.User;
import com.example.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User create(CreateUserDto userdto, PasswordEncoder passwordEncoder){

        User user = new User(userdto, passwordEncoder);
        // TODO POSLATI MEJL
        return this.userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        // todo promeniti na find i raditi proveru
        return userRepository.getById(id);
    }

    public User activateUser(Long id) {
        User u = userRepository.getById(id);
        u.setActive(true);
        return userRepository.save(u);
    }

    public User deactivateUser(Long id) {
        User u = userRepository.getById(id);
        u.setActive(false);
        return userRepository.save(u);
    }

    public User loyal(Long id) {
        User u = userRepository.getById(id);
        u.setLoyalty(true);

        return userRepository.save(u);
    }

    public User deloyal(Long id) {
        User u = userRepository.getById(id);
        u.setLoyalty(false);

        return userRepository.save(u);
    }

    public User editUser(Long id, EditUserDto editUserDto, PasswordEncoder passwordEncoder) {
        User u = userRepository.getById(id);

        u.setFirst_name(editUserDto.getFirst_name() != null && !editUserDto.getFirst_name().isEmpty() ? editUserDto.getFirst_name() : u.getFirst_name());
        u.setLast_name(editUserDto.getLast_name() != null && !editUserDto.getLast_name().isEmpty() ? editUserDto.getLast_name() : u.getLast_name());
        u.setUsername(editUserDto.getUsername() != null && !editUserDto.getUsername().isEmpty() ? editUserDto.getUsername() : u.getUsername());
        // todo ovo je sa encoderom
//        u.setPassword(editUserDto.getPassword() != null && !editUserDto.getPassword().isEmpty() ?  passwordEncoder.encode(editUserDto.getPassword()) : u.getPassword());
        u.setPassword(editUserDto.getPassword() != null && !editUserDto.getPassword().isEmpty() ?  editUserDto.getPassword() : u.getPassword());
        u.setBirthday(editUserDto.getBirthday() != null ? editUserDto.getBirthday() : u.getBirthday());

        return userRepository.save(u);
    }

    public ResponseEntity<?> deteleUser(Long id) {
        User u = userRepository.getById(id);
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
