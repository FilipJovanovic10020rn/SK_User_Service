package com.example.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyServiceBookDTO {

    private Long cilentID;
    private Long managerID;

    private String workoutName;

    private Date date;



}
