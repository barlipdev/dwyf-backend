package com.barlipdev.dwyf.model.stats;

import com.barlipdev.dwyf.model.recipe.MatchedRecipe;
import com.barlipdev.dwyf.model.recipe.Recipe;
import com.barlipdev.dwyf.model.recipe.RecipeStatus;
import com.barlipdev.dwyf.model.user.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("Performing Recipe")
public class PerformingRecipe {

    @Id
    private String id;
    private String userId;
    private MatchedRecipe matchedRecipe;
    private LocalDate createdOn;
    private RecipeStatus recipeStatus;

    public PerformingRecipe(){
        this.createdOn = LocalDate.now();
        this.recipeStatus = RecipeStatus.IN_PROGRESS;
    }

    public PerformingRecipe(String userId, MatchedRecipe matchedRecipe) {
        this.userId = userId;
        this.matchedRecipe = matchedRecipe;
        this.recipeStatus = RecipeStatus.IN_PROGRESS;
        this.createdOn = LocalDate.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public MatchedRecipe getMatchedRecipe() {
        return matchedRecipe;
    }

    public void setMatchedRecipe(MatchedRecipe matchedRecipe) {
        this.matchedRecipe = matchedRecipe;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public RecipeStatus getRecipeStatus() {
        return recipeStatus;
    }

    public void setRecipeStatus(RecipeStatus recipeStatus) {
        this.recipeStatus = recipeStatus;
    }
}
