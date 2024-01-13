package com.example.userservice.dtos;

import com.example.userservice.model.User;
import com.example.userservice.model.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class NotifyUserDto {

    private Long id;

    @JsonProperty("first_name")
    private String first_name;
//    @JsonProperty("last_name")
    private String last_name;
    private String username;
    private String email;
    private Date birthday;
    private UserType userType;
    private int workout_count;
    private String workout_room_name;
    private Date date_of_employment;
    private boolean verified;
    private boolean active;
    private boolean loyalty;

    public NotifyUserDto(){

    }
    public NotifyUserDto(User user){
        this.id = user.getId();
        this.first_name =           user.getFirst_name();
        this.last_name =            user.getLast_name();
        this.username =             user.getUsername();
        this.email =                user.getEmail();
        this.birthday =             user.getBirthday();
        this.userType =             user.getUserType();
        this.workout_count =        user.getWorkout_count();
        this.verified =             user.isVerified();
        this.active =               user.isActive();
        this.loyalty =              user.isLoyalty();
        this.workout_room_name=     user.getWorkout_room_name();
        this.date_of_employment=    user.getDate_of_employment();
    }

    public void mapForClient(User user) {
        this.id = user.getId();
        this.first_name =       user.getFirst_name();
        this.last_name =        user.getLast_name();
        this.username =         user.getUsername();
        this.email =            user.getEmail();
        this.birthday =         user.getBirthday();
        this.userType =         user.getUserType();
        this.workout_count =    user.getWorkout_count();
        this.verified =         user.isVerified();
        this.active =           user.isActive();
        this.loyalty =          user.isLoyalty();
    }

    public void mapForManager(User user) {
        this.id = user.getId();
        this.first_name =       user.getFirst_name();
        this.last_name =        user.getLast_name();
        this.username =         user.getUsername();
        this.email =            user.getEmail();
        this.birthday =         user.getBirthday();
        this.userType =         user.getUserType();
        this.verified =         user.isVerified();
        this.active =           user.isActive();
        this.workout_room_name= user.getWorkout_room_name();
        this.date_of_employment=user.getDate_of_employment();
    }

}
