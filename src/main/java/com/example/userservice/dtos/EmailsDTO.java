package com.example.userservice.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
public class EmailsDTO {

    private String clientEmail;
    private String managerEmail;
}
