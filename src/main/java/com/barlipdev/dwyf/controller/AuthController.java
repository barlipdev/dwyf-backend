package com.barlipdev.dwyf.controller;

import com.barlipdev.dwyf.model.LoginData;
import com.barlipdev.dwyf.model.User;
import com.barlipdev.dwyf.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public User login(@RequestBody LoginData loginData){
        return authService.login(loginData);
    }

    @PostMapping("/v2/login/{email}&{password}")
    public User loginV2(@PathVariable String email, @PathVariable String password){
        return authService.login(new LoginData(email,password));
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) throws IOException {
        return authService.register(user);
    }

}
