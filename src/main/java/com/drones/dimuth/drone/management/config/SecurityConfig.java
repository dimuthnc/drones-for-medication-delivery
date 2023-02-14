//package com.drones.dimuth.drone.management.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@EnableWebSecurity
//public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
//        return http.build();
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password("{bcrypt}$2a$10$TyfBFH039pvsI9RY0rNgu.iGT57NmXoCOGVi1O9g/kVZFTWdR6OLi")
//                // password is "password"
//                .roles("ADMIN");
//    }
//
//}
//
//
