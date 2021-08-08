package com.barlipdev.dwyf.controller;

import com.barlipdev.dwyf.model.Recipe;
import com.barlipdev.dwyf.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @PostMapping("/recipe")
    public Recipe add(@RequestBody Recipe recipe){
        return recipeService.add(recipe);
    }

    @GetMapping("/recipe/{id}")
    public Recipe findById(@PathVariable String id){
        return recipeService.findById(id);
    }

    @GetMapping("/recipe")
    public List<Recipe> findAll(){
        return recipeService.findAll();
    }

    @GetMapping("/recipe/chart")
    public List<Integer> getRecipesCountByTypes(){
        return recipeService.getRecipesCountByTypes();
    }

    @GetMapping("/recipe/best/{userId}")
    public Recipe getPrefferedRecipe(@PathVariable String userId){
        return recipeService.getPrefferedRecipe(userId);
    }

    @DeleteMapping("/recipe")
    public void delete(@RequestBody Recipe recipe){
        recipeService.delete(recipe);
    }

}
