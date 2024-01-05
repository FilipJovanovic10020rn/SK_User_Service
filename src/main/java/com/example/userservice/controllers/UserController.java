package com.example.userservice.controllers;

import com.example.userservice.dtos.EditUserDto;
import com.example.userservice.dtos.CreateUserDto;
import com.example.userservice.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody CreateUserDto createUserDto){
        return ResponseEntity.ok(userService.create(createUserDto,passwordEncoder));
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers(){
        return  ResponseEntity.ok(userService.findAll());
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserById(@RequestParam Long id){
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping(value = "/activate/{id}")
    public ResponseEntity<?> activateUser(@RequestParam Long id){
        return  ResponseEntity.ok(userService.activateUser(id));
    }

    @PutMapping(value = "/deactivate/{id}")
    public ResponseEntity<?> deactivateUser(@RequestParam Long id){
        return  ResponseEntity.ok(userService.deactivateUser(id));
    }

    @PutMapping(value = "/loyal/{id}")
    public ResponseEntity<?> loyalUser(@RequestParam Long id){
        return  ResponseEntity.ok(userService.loyal(id));
    }

    @PutMapping(value = "/deloyal/{id}")
    public ResponseEntity<?> deloyalUser(@RequestParam Long id){
        return  ResponseEntity.ok(userService.deloyal(id));
    }

    //TODO ovo dodati i ostalima samo za sebe
    @PutMapping(value = "/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editUser(@RequestParam Long id, @RequestBody EditUserDto editUserDto){
        return ResponseEntity.ok(userService.editUser(id,editUserDto,passwordEncoder));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@RequestParam Long id){
        return ResponseEntity.ok(userService.deteleUser(id));
    }

}