package com.barlipdev.dwyf.repository;

import com.barlipdev.dwyf.model.product.ProductOpenFood;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface OpenFoodProductRepository extends MongoRepository<ProductOpenFood,String> {

}
