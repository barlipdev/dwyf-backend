package com.barlipdev.dwyf.service;

import com.barlipdev.dwyf.model.LoginData;
import com.barlipdev.dwyf.model.LoginResponse;
import com.barlipdev.dwyf.model.User;
import com.barlipdev.dwyf.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    public User register(@RequestBody User newUser) throws IOException {
        newUser.setAvatarUrl("http://51.68.139.166/img/default.png");
        newUser.setProductList(new ArrayList<>());
        newUser.setCreatedIn(LocalDate.now());
        return userRepository.insert(newUser);
    }

    public LoginResponse login(LoginData loginData){

        User findedUser = (User) userRepository.findByEmail(loginData.getEmail()).get();
        String authToken = "";

        if (findedUser != null && findedUser.getPassword().equals(loginData.getPassword())){
            authToken = Jwts.builder()
                    .setSubject(loginData.getEmail())
                    .claim("email",loginData.getEmail())
                    .claim("password",loginData.getPassword())
                    .claim("userRole",findedUser.getUserRole())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()+ 86400000))
                    .signWith(SignatureAlgorithm.HS512,"7aPc$VED<-Qr8)E".getBytes())
                    .compact();
        }

        return new LoginResponse(authToken,findedUser);
    }

}
