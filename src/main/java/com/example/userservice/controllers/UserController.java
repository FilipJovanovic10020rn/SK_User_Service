package com.example.userservice.controllers;

import com.example.userservice.dtos.*;
import com.example.userservice.model.UserType;
import com.example.userservice.security.CheckSecurity;
import com.example.userservice.security.service.TokenService;
import com.example.userservice.services.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping(value = "/register/manager", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerManager(@RequestBody RegisterManagerDto registerManagerDto){
        return ResponseEntity.ok(userService.registerManager(registerManagerDto));
    }
    @PostMapping(value = "/register/client", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerClient(@RequestBody RegisterClientDto registerClientDto){
        return ResponseEntity.ok(userService.registerClient(registerClientDto));
    }
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        return ResponseEntity.ok(userService.login(loginDto));
    }

    @PostMapping(value = "/admin/createManager", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity(roles = {UserType.ADMIN})
    public ResponseEntity<?> createManager(@RequestHeader("Authorization") String authorization, @RequestBody CreateManagerDto createManagerDto){
        return ResponseEntity.ok(userService.create(createManagerDto,passwordEncoder));
    }

    /*
    {
        "first_name": "zadnjiM",
        "last_name": "test",
        "username": "zadnjiM",
        "password": "123",
        "email": "3test.com",
        "birthday": "2024-01-10T19:49:29.000+00:00",
        "userType": "MANAGER",
        "workout_room_name": "sala1",
        "date_of_employment": "2024-01-10T19:49:29.000+00:00",
        "verified": true,
        "active": true
    }
     */

    @PostMapping(value = "/admin/createClient", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity(roles = {UserType.ADMIN})
    public ResponseEntity<?> createClient(@RequestHeader("Authorization") String authorization, @RequestBody CreateClientDto createClientDto){
        return ResponseEntity.ok(userService.create(createClientDto,passwordEncoder));
    }

    /*
    {
        "first_name": "ZadnjiC",
        "last_name": "test",
        "username": "zadnjiC",
        "password": "123",
        "email": "tes3.com",
        "birthday": "2024-01-10T19:49:29.000+00:00",
        "userType": "CLIENT",
        "workout_count": "0",
        "verified": true,
        "active": true,
        "loyalty": false
    }
     */

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity(roles = {UserType.ADMIN, UserType.MANAGER, UserType.CLIENT})
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String authorization){
        return  ResponseEntity.ok(userService.findAll(authorization));
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity(roles = {UserType.ADMIN, UserType.MANAGER})
    public ResponseEntity<?> getUserById(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id){
        return ResponseEntity.ok(userService.getById(authorization,id));
    }

    @PutMapping(value = "/admin/activate/{id}")
    @CheckSecurity(roles = {UserType.ADMIN})
    public ResponseEntity<?> activateUser(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id){
        return  ResponseEntity.ok(userService.activateUser(id));
    }

    @PutMapping(value = "/admin/deactivate/{id}")
    @CheckSecurity(roles = {UserType.ADMIN})
    public ResponseEntity<?> deactivateUser(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id){
        return  ResponseEntity.ok(userService.deactivateUser(authorization, id));
    }

    @PutMapping(value = "/loyal/{id}")
    @CheckSecurity(roles = {UserType.ADMIN, UserType.MANAGER})
    public ResponseEntity<?> loyalUser(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id){
        return  ResponseEntity.ok(userService.loyal(id));
    }

    @PutMapping(value = "/deloyal/{id}")
    @CheckSecurity(roles = {UserType.ADMIN, UserType.MANAGER})
    public ResponseEntity<?> deloyalUser(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id){
        return  ResponseEntity.ok(userService.deloyal(id));
    }

    @PutMapping(value = "/editprofile", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity(roles = {UserType.ADMIN, UserType.CLIENT, UserType.MANAGER})
    public ResponseEntity<?> editSelf(@RequestHeader("Authorization") String authorization, @RequestBody EditClientDto editClientDto){
        return ResponseEntity.ok(userService.editSelf(authorization,editClientDto));
    }
    /*
    {
        "first_name": "CLIENT",
        "last_name": "test",
        "email": "testMAIL",
        "username": "client"
    }
     */


    @PutMapping(value = "/changepassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity(roles = {UserType.MANAGER, UserType.CLIENT})
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String authorization, @RequestBody ChangePasswordDto changePasswordDto){
        return ResponseEntity.ok(userService.changePassword(authorization,changePasswordDto));
    }
            /*
    {
        "old_password": "123",
        "new_password": "321"
    }
             */

    @PutMapping(value = "/admin/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity(roles = {UserType.ADMIN})
    public ResponseEntity<?> editUser(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id, @RequestBody EditUserDto editUserDto){
        return ResponseEntity.ok(userService.editUser(authorization,id,editUserDto,passwordEncoder));
    }

        /*
    {
        "first_name": "CLIENT",
        "last_name": "test",
        "username": "client",
        "password": "123",

        "first_name": "MANAGER",
        "last_name": "test",
        "username": "manager",
        "password": "123",
    }
     */

    @DeleteMapping(value = "/admin/delete/{id}")
    @CheckSecurity(roles = {UserType.ADMIN})
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id){
        return ResponseEntity.ok(userService.deteleUser(authorization,id));
    }

    @GetMapping(value = "/get-workout-count/{id}")
    public ResponseEntity<?> getWorkoutCount(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.getWorkoutCount(id));
    }

}