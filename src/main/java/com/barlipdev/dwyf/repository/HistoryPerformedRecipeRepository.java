package com.barlipdev.dwyf.repository;

import com.barlipdev.dwyf.model.stats.HistoryPerformedRecipe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoryPerformedRecipeRepository extends MongoRepository<HistoryPerformedRecipe,String> {
}
