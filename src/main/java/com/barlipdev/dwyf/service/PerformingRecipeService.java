package com.barlipdev.dwyf.service;

import com.barlipdev.dwyf.model.product.Product;
import com.barlipdev.dwyf.model.recipe.MatchedRecipe;
import com.barlipdev.dwyf.model.recipe.Recipe;
import com.barlipdev.dwyf.model.recipe.RecipeStatus;
import com.barlipdev.dwyf.model.recipe.RemovedProduct;
import com.barlipdev.dwyf.model.user.User;
import com.barlipdev.dwyf.model.stats.PerformingRecipe;
import com.barlipdev.dwyf.repository.PerformingRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PerformingRecipeService {

    @Autowired
    PerformingRecipeRepository performingRecipeRepository;
    @Autowired
    UserService userService;

    public PerformingRecipe addPerformedRecipe(String userId, MatchedRecipe matchedRecipe){

        PerformingRecipe performingRecipe = new PerformingRecipe(userId,matchedRecipe);

        return performingRecipeRepository.save(performingRecipe);
    }

    public List<PerformingRecipe> findAllByUserId(String userId){
        return performingRecipeRepository.findAllByUserId(userId, Sort.by(Sort.Direction.DESC,"createdOn")).orElseThrow();
    }

    public PerformingRecipe updatePerformingRecipe(PerformingRecipe performingRecipe){
        return performingRecipeRepository.save(performingRecipe);
    }


    public void deletePerformingRecipe(PerformingRecipe performingRecipe){
        performingRecipeRepository.delete(performingRecipe);
    }

    public PerformingRecipe performRecipe(String userId, PerformingRecipe performingRecipe){
        User user = userService.findById(userId);

        for (RemovedProduct removedProduct : performingRecipe.getMatchedRecipe().getRemovedProducts()) {
            for (Product userProduct : user.getProductList()) {
                if (removedProduct.getName().equals(userProduct.getName())) {
                    userProduct.setCount(roundCount(userProduct.getCount() - removedProduct.getCount()));
                }
            }
        }
        performingRecipe.setRecipeStatus(RecipeStatus.DONE);
        userService.update(user);

        return performingRecipeRepository.save(performingRecipe);
    }

    public double roundCount(double value) {
        int precision = 2;
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(precision, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

}
