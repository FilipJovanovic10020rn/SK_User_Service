package com.example.userservice.dtos;

import com.example.userservice.model.UserType;
import lombok.Data;

import java.util.Date;

@Data
public class CreateClientDto extends CreateUserDto{
    private int workout_count;
    private boolean loyalty;
}
