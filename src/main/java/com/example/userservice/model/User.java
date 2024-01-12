package com.example.userservice.model;

import com.example.userservice.dtos.CreateClientDto;
import com.example.userservice.dtos.CreateManagerDto;
import com.example.userservice.dtos.RegisterClientDto;
import com.example.userservice.dtos.RegisterManagerDto;
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
    private Long id; // ovo je i membership id
    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private String email;
    private Date birthday;
    private UserType userType;
    private int workout_count;
    private String workout_room_name;
    private Date date_of_employment;

    private boolean verified;
    private boolean active;
    private boolean loyalty;


    /*
     * Kreiranje clienta za admina
     */
    public User(CreateClientDto createClientDto, PasswordEncoder passwordEncoder) {
        this.first_name =   createClientDto.getFirst_name();
        this.last_name =    createClientDto.getLast_name();
        this.username =     createClientDto.getUsername();
        // todo ovo otkomentarisati
//        String encodedPassword = passwordEncoder.encode(createClientDto.getPassword());
//        this.password = encodedPassword;
        this.password =     createClientDto.getPassword();
        this.email =        createClientDto.getEmail();
        this.birthday =     createClientDto.getBirthday();
        this.userType =     UserType.CLIENT;
        this.workout_count = createClientDto.getWorkout_count();
        // todo  Da bi mogli rucno da namestamo u testiranju vratiti na false
        this.verified =     createClientDto.isVerified(); // inicijalno false dok ne verifikuje emial
//        this.verified =     false;
        this.active =       createClientDto.isActive();
        this.loyalty =      createClientDto.isLoyalty();
    }

    /*
     * Kreiranje managera za admina
     */
    public User(CreateManagerDto createManagerDto, PasswordEncoder passwordEncoder) {

        this.first_name =   createManagerDto.getFirst_name();
        this.last_name =    createManagerDto.getLast_name();
        this.username =     createManagerDto.getUsername();
        // todo ovo otkomentarisati
//        String encodedPassword = passwordEncoder.encode(createClientDto.getPassword());
//        this.password = encodedPassword;
        this.password =     createManagerDto.getPassword();
        this.email =        createManagerDto.getEmail();
        this.birthday =     createManagerDto.getBirthday();
        this.userType =     UserType.MANAGER;
        this.date_of_employment = createManagerDto.getDate_of_employment();
        this.workout_room_name = createManagerDto.getWorkout_room_name();
        // todo  Da bi mogli rucno da namestamo u testiranju vratiti na false
        this.verified =     createManagerDto.isVerified(); // inicijalno false dok ne verifikuje emial
//        this.verified =     false;
        this.active =       createManagerDto.isActive();
    }


    public User() {

    }

    /*
     * Bootstrap za admina i rucno kreiranje
     */
    public User(String first_name, String last_name, String username, String password, String email, Date birthday, UserType userType,boolean verified, boolean active, boolean loyalty) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.userType = userType;
        this.verified = verified;
        this.active = active;
        this.loyalty = loyalty;
    }

    /*
     * Registracija za managera
     */
    public User(RegisterManagerDto registerManagerDto, PasswordEncoder passwordEncoder) {
        this.first_name =   registerManagerDto.getFirst_name();
        this.last_name =    registerManagerDto.getLast_name();
        this.username =     registerManagerDto.getUsername();
        // todo ovo otkomentarisati
//        String encodedPassword = passwordEncoder.encode(registerManagerDto.getPassword());
        this.password =     registerManagerDto.getPassword();
        this.email =        registerManagerDto.getEmail();
        this.birthday =     registerManagerDto.getBirthday();
        this.userType =     UserType.MANAGER;
        this.verified =     false; // inicijalno false dok ne verifikuje emial
        this.active =       true;
        this.workout_room_name = registerManagerDto.getWorkout_room_name();
        this.date_of_employment = registerManagerDto.getDate_of_employment();
    }

    /*
    * Registracija za clienta
     */
    public User(RegisterClientDto registerClientDto, PasswordEncoder passwordEncoder) {
        this.first_name =   registerClientDto.getFirst_name();
        this.last_name =    registerClientDto.getLast_name();
        this.username =     registerClientDto.getUsername();
        // todo ovo otkomentarisati
//        String encodedPassword = passwordEncoder.encode(registerClientDto.getPassword());
        this.password =     registerClientDto.getPassword();
        this.email =        registerClientDto.getEmail();
        this.birthday =     registerClientDto.getBirthday();
        this.userType =     UserType.CLIENT;
        this.workout_count = 0;
        this.verified =     false; // inicijalno false dok ne verifikuje emial
        this.active =       true;
        this.loyalty =      false;
    }


}
