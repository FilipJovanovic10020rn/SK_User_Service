package com.example.userservice.model;

import com.example.userservice.dtos.CreateUserDto;
import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private String email;
    private Date birthday;
    private UserType userType;
    private boolean verified;
    private boolean active;
    private boolean loyalty;

    public User(CreateUserDto createUserDto, PasswordEncoder passwordEncoder) {
        this.first_name =   createUserDto.getFirst_name();
        this.last_name =    createUserDto.getLast_name();
        this.username =     createUserDto.getUsername();
        // todo ovo otkomentarisati
//        String encodedPassword = passwordEncoder.encode(createUserDto.getPassword());
//        this.password = encodedPassword;
        this.password =     createUserDto.getPassword();
        this.email =        createUserDto.getEmail();
        this.birthday =     createUserDto.getBirthday();
        this.userType =     createUserDto.getUserType();
//        this.verified =     false; // inicijalno false dok ne verifikuje emial
        this.verified =     true; // test
        this.active =       createUserDto.isActive();
        this.loyalty =      createUserDto.isLoyalty();
    }

    public User() {

    }
}
