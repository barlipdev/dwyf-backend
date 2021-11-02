package com.barlipdev.dwyf.repository;

import com.barlipdev.dwyf.model.recipe.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe,String> {

    Optional<Long> countByFoodType(String foodType);

}
