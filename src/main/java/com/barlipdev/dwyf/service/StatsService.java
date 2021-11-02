package com.barlipdev.dwyf.service;

import com.barlipdev.dwyf.model.user.User;
import com.barlipdev.dwyf.model.stats.RadarChartRecipes;
import com.barlipdev.dwyf.repository.RecipeRepository;
import com.barlipdev.dwyf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StatsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RecipeRepository recipeRepository;


    public List<User> getLatestJoinedUsers(){
        List<User> latestUsers = userRepository.findAll(PageRequest.of(0,6,Sort.Direction.DESC,"createdIn")).toList();
        return latestUsers;
    }

    public List<RadarChartRecipes> getRecipesCountToRadarChart(){
        List<String> foodTypes = new ArrayList<>(Arrays.asList("Sniadanie","Obiad","Deser","Kolacja","Inne"));
        List<RadarChartRecipes> radarChartRecipes = new ArrayList<>();

        foodTypes.forEach( foodType -> {
            RadarChartRecipes radarChartRecipe = new RadarChartRecipes();
            long count;

            count = recipeRepository.countByFoodType(foodType).orElse(Long.valueOf(0));
            radarChartRecipe.setFoodType(foodType);
            radarChartRecipe.setCount(count);
            radarChartRecipes.add(radarChartRecipe);

        });

        return radarChartRecipes;
    }

}
