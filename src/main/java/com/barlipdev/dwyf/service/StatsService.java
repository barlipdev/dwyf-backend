package com.barlipdev.dwyf.service;

import com.barlipdev.dwyf.model.User;
import com.barlipdev.dwyf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsService {

    @Autowired
    UserRepository userRepository;


    public List<User> getLatestJoinedUsers(){
        List<User> latestUsers = userRepository.findAll(PageRequest.of(0,6,Sort.Direction.DESC,"createdIn")).toList();
        return latestUsers;
    }

}
