package com.barlipdev.dwyf.service;

import com.barlipdev.dwyf.model.recipe.Recipe;
import com.barlipdev.dwyf.model.user.User;
import com.barlipdev.dwyf.model.stats.HistoryPerformedRecipe;
import com.barlipdev.dwyf.repository.HistoryPerformedRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryPerformedRecipeService {

    @Autowired
    HistoryPerformedRecipeRepository historyPerformedRecipeRepository;

    public HistoryPerformedRecipe addPerformedRecipe(User user, Recipe recipe){
        HistoryPerformedRecipe historyPerformedRecipe = new HistoryPerformedRecipe();
        historyPerformedRecipe.setRecipe(recipe);
        historyPerformedRecipe.setUser(user);

        return historyPerformedRecipeRepository.save(historyPerformedRecipe);
    }

}
