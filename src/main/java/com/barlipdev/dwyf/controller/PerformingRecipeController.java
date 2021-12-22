package com.barlipdev.dwyf.controller;

import com.barlipdev.dwyf.model.stats.PerformingRecipe;
import com.barlipdev.dwyf.service.PerformingRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PerformingRecipeController {

    @Autowired
    PerformingRecipeService performingRecipeService;

    @GetMapping("/performing/all")
    public List<PerformingRecipe> getAllPerformingRecipesByUserId(@RequestParam("userId") String userId){
        return performingRecipeService.findAllByUserId(userId);
    }

    @PostMapping("/performing/update")
    public PerformingRecipe update(@RequestBody PerformingRecipe performingRecipe){
        return performingRecipeService.updatePerformingRecipe(performingRecipe);
    }

    @DeleteMapping("/performing")
    public void remove(@RequestBody PerformingRecipe performingRecipe){
        performingRecipeService.deletePerformingRecipe(performingRecipe);
    }

    @PostMapping("/performing/perform")
    public PerformingRecipe performRecipe(@RequestParam("userId") String userId, @RequestBody PerformingRecipe performingRecipe){
        return performingRecipeService.performRecipe(userId, performingRecipe);
    }

}
