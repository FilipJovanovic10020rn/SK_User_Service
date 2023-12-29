//package com.example.userservice.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors()
//                .and()
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/api/public").permitAll() // Public endpoint
//                .antMatchers("/api/private").authenticated() // Secured endpoint
//                .anyRequest().authenticated();
////                .and()
////                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
////                .addFilter(new JwtAuthorizationFilter(authenticationManager()));
//    }
//
//}