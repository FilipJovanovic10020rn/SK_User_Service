package com.example.userservice.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private String email;
    private Date birthday;
    private UserType userType;
    private boolean verified;
    private boolean active;
    private boolean loyalty;


}
