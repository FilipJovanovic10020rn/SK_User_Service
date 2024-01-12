package com.example.userservice.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class EditClientDto {
    private String first_name;
    private String last_name;
    private String email;
    private String username;
}
