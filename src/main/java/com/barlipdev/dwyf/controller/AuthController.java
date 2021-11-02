package com.barlipdev.dwyf.controller;

import com.barlipdev.dwyf.model.auth.LoginData;
import com.barlipdev.dwyf.model.auth.LoginResponse;
import com.barlipdev.dwyf.model.user.User;
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
    public LoginResponse login(@RequestBody LoginData loginData){

        return authService.login(loginData);
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) throws IOException {
        return authService.register(user);
    }

}
