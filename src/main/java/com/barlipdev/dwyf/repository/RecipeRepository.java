package com.barlipdev.dwyf.repository;

import com.barlipdev.dwyf.model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe,String> {

}
