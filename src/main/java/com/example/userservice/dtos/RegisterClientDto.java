package com.example.userservice.dtos;

import com.example.userservice.model.UserType;
import lombok.Data;

import java.util.Date;

@Data
public class RegisterClientDto {
    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private String email;
    private Date birthday;

}