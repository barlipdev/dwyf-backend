package com.barlipdev.dwyf.model.stats;

import com.barlipdev.dwyf.model.recipe.Recipe;
import com.barlipdev.dwyf.model.recipe.RecipeStatus;
import com.barlipdev.dwyf.model.user.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("HistoryPerformedRecipe")
public class HistoryPerformedRecipe {

    @Id
    private String id;
    @DBRef
    private User user;
    @DBRef
    private Recipe recipe;
    @CreatedDate
    private LocalDate createdOn;
    private RecipeStatus recipeStatus;

    public HistoryPerformedRecipe(){}

    public HistoryPerformedRecipe(String id, User user, Recipe recipe, RecipeStatus recipeStatus) {
        this.id = id;
        this.user = user;
        this.recipe = recipe;
        this.recipeStatus = recipeStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
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
