package com.barlipdev.dwyf.controller;

import com.barlipdev.dwyf.model.LoginData;
import com.barlipdev.dwyf.model.User;
import com.barlipdev.dwyf.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.io.IOException;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public User login(@RequestBody LoginData loginData){
        return authService.login(loginData);
    }

    @PostMapping("/login/v2")
    public User loginV2(@PathParam("email") String email, @PathParam("password") String password){
        return authService.login(new LoginData(email,password));
    }

    @PostMapping("/register")
    public User register(@PathParam("username") String username,@PathParam("email") String email, @PathParam("password") String password) throws IOException {
        return authService.register(username, email, password);
    }

}
