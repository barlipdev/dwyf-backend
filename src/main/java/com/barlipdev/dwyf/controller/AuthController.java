package com.barlipdev.dwyf.controller;

import com.barlipdev.dwyf.model.User;
import com.barlipdev.dwyf.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return authService.login(user);
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) throws IOException {
        return authService.register(user);
    }

}
