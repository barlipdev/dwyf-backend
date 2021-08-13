package com.barlipdev.dwyf.service;

import com.barlipdev.dwyf.model.LoginData;
import com.barlipdev.dwyf.model.User;
import com.barlipdev.dwyf.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    public User register(String username, String email, String password) throws IOException {
        User user = new User(username,email,password);
        return userRepository.insert(user);
    }

    public User login(LoginData loginData){

        User findedUser = (User) userRepository.findByEmail(loginData.getEmail()).orElseThrow();
        String auth_token = "";

        if (findedUser != null && findedUser.getPassword().equals(loginData.getPassword())){
            auth_token = Jwts.builder()
                    .setSubject(loginData.getEmail())
                    .claim("email",loginData.getEmail())
                    .claim("password",loginData.getPassword())
                    .claim("id",findedUser.getId())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()+ 86400000))
                    .signWith(SignatureAlgorithm.HS512,"asffddfs$%&*".getBytes())
                    .compact() + "UID"+findedUser.getId();
        }

        findedUser.setAuth_token(auth_token);
        return findedUser;
    }

}
