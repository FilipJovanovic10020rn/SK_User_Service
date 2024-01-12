package com.example.userservice.security;

import com.example.userservice.model.UserType;
import org.springframework.stereotype.Repository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repository
public @interface CheckSecurity {
    UserType[] roles() default {};
}
