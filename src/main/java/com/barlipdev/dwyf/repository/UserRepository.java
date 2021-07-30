package com.barlipdev.dwyf.repository;

import com.barlipdev.dwyf.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
