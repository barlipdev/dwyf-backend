package com.barlipdev.dwyf.repository;

import com.barlipdev.dwyf.model.product.ProductOpenFood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface OpenFoodProductRepository extends MongoRepository<ProductOpenFood,String> {

    Page<ProductOpenFood> findAll(Pageable pageable);
    Page<ProductOpenFood> findByProductNameContains(String name, Pageable pageable);

}
