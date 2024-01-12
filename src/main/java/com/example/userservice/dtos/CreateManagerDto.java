package com.example.userservice.dtos;

import com.example.userservice.model.UserType;
import lombok.Data;

import java.util.Date;

@Data
public class CreateManagerDto extends CreateUserDto {
    private String workout_room_name;
    private Date date_of_employment;

}
