package com.barlipdev.dwyf.repository;

import com.barlipdev.dwyf.model.stats.PerformingRecipe;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PerformingRecipeRepository extends MongoRepository<PerformingRecipe,String> {

    Optional<List<PerformingRecipe>> findAllByUserId(String id, Sort sort);

}
