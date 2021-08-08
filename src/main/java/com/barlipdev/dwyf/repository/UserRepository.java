package com.barlipdev.dwyf.repository;

import com.barlipdev.dwyf.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<Object> findByEmail(String email);
}
