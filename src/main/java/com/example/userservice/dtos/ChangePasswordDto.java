package com.example.userservice.dtos;

import lombok.Data;

@Data
public class ChangePasswordDto {
    String old_password;
    String new_password;
}
