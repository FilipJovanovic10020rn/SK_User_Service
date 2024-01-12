package com.example.userservice.repositories;

import com.example.userservice.model.User;
import com.example.userservice.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsernameAndPassword(String username, String password);

    List<User> findUsersByUserTypeEquals(UserType userType);

    Optional<User> findUsersByUsernameEqualsIgnoreCase(String username);


}
