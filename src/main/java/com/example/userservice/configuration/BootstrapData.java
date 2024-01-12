package com.example.userservice.configuration;

import com.example.userservice.model.User;
import com.example.userservice.model.UserType;
import com.example.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BootstrapData(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        // TODO encode pass ovde
        User user = new User(admin_name,admin_lastname,admin_username,admin_password,admin_email,admin_birtday,admin_type,admin_verified,admin_active,admin_loyal);

        Optional<User> adminUser = userRepository.findUserByEmail(admin_email);
        if(adminUser.isEmpty()){
            this.userRepository.save(user);

        }
    }
}
