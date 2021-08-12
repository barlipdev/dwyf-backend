package com.barlipdev.dwyf.service;

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

    public User register(User user) throws IOException {
        return userRepository.insert(user);
    }

    public User login(User user){

        User findedUser = (User) userRepository.findByEmail(user.getEmail()).orElseThrow();
        String auth_token = "";

        if (findedUser != null && findedUser.getPassword().equals(user.getPassword())){
            auth_token = Jwts.builder()
                    .setSubject(user.getEmail())
                    .claim("email",user.getEmail())
                    .claim("password",user.getPassword())
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
