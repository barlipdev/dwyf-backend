package com.barlipdev.dwyf.controller;

import com.barlipdev.dwyf.model.user.User;
import com.barlipdev.dwyf.model.stats.RadarChartRecipes;
import com.barlipdev.dwyf.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatsController {

    @Autowired
    StatsService statsService;

    @GetMapping("/stats/latestUsers")
    public List<User> getLatestUsers(){
        return statsService.getLatestJoinedUsers();
    }

    @GetMapping("/stats/radar")
    public List<RadarChartRecipes> getRecipesCountToRadarChart(){
        return statsService.getRecipesCountToRadarChart();
    }

}
