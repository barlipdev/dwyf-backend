package com.barlipdev.dwyf.controller;

import com.barlipdev.dwyf.model.product.ProductFilter;
import com.barlipdev.dwyf.model.recipe.FoodTypeFilter;
import com.barlipdev.dwyf.model.recipe.MatchedRecipe;
import com.barlipdev.dwyf.model.recipe.Recipe;
import com.barlipdev.dwyf.model.stats.PerformingRecipe;
import com.barlipdev.dwyf.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @PostMapping("/recipe/add")
    public Recipe add(@RequestBody Recipe recipe){
        return recipeService.add(recipe);
    }

    @GetMapping("/recipe/{id}")
    public Recipe findById(@PathVariable String id){
        return recipeService.findById(id);
    }

    @GetMapping("/recipe/all")
    public List<Recipe> findAll(){
        return recipeService.findAll();
    }

    @GetMapping("/recipe/chart")
    public List<Integer> getRecipesCountByTypes(){
        return recipeService.getRecipesCountByTypes();
    }

    @GetMapping("/recipe/best")
    public MatchedRecipe getPrefferedRecipe(@RequestParam("userId") String userId,
                                            @RequestParam("productFilter")ProductFilter productFilter,
                                            @RequestParam("foodTypeFilter")FoodTypeFilter foodTypeFilter) {
        return recipeService.getPrefferedRecipe(userId,productFilter,foodTypeFilter);
    }

    @PostMapping("/recipe/best/addPerform")
    public PerformingRecipe addPerform(@RequestParam("userId") String userId, @RequestBody MatchedRecipe matchedRecipe){
        return recipeService.addPerform(userId,matchedRecipe);
    }

    @DeleteMapping("/recipe")
    public void delete(@RequestBody Recipe recipe){
        recipeService.delete(recipe);
    }

    @DeleteMapping("/recipe/{id}")
    public void deleteById(@PathVariable String id){
        Recipe recipe = recipeService.findById(id);
        recipeService.delete(recipe);
    }

}
