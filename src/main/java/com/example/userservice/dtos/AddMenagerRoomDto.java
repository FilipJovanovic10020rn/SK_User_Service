package com.example.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMenagerRoomDto {
    private Long managerId;
    private String name; // naziv room-a
}
