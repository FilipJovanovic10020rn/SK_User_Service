package com.example.userservice.configuration;

import com.example.userservice.model.UserType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class BootstrapData implements CommandLineRunner {

    private static final String admin_name = "admin";
    private static final String admin_lastname = "admin";
    private static final String admin_username = "admin";
    private static final String admin_email = "admin.com";
    private static final Date admin_birtday = new Date();
    private static final String admin_password = "123";
    private static final UserType admin_type = UserType.ADMIN;
    private static final boolean admin_verified = true;
    private static final boolean admin_active = true;
    private static final boolean admin_loyal = true;

    @Override
    public void run(String... args) throws Exception {

    }
}
