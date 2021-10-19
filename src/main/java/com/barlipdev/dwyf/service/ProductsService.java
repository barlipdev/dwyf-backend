package com.barlipdev.dwyf.service;

import com.barlipdev.dwyf.model.product.ProductOpenFood;
import com.barlipdev.dwyf.repository.OpenFoodProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProductsService {

    @Autowired
    OpenFoodProductRepository openFoodProductRepository;

    public ProductOpenFood getProductById(String id){
        return openFoodProductRepository.findById(id).orElseThrow();
    }

    public Page<ProductOpenFood> getProducts(Optional<Integer> page, Optional<Integer> size){

        Pageable pageable = PageRequest.of(page.orElse(0),size.orElse(0));

        return openFoodProductRepository.findAll(pageable);
    }

    public Page<ProductOpenFood> getProductsByName(Optional<Integer> page, Optional<Integer> size, String name){
        Pageable pageable = PageRequest.of(page.orElse(0),size.orElse(0));
        return openFoodProductRepository.findByProductNameContains(name,pageable);
    }

    public int getCountOkProducts(){
        List<ProductOpenFood> productOpenFoodList = openFoodProductRepository.findAll();
        AtomicInteger counter = new AtomicInteger(0);
        productOpenFoodList.forEach(productOpenFood -> {
            if (productOpenFood.getProductName() != null && productOpenFood.getQuantity() != null && productOpenFood.getBrands() != null){
                counter.getAndIncrement();
            }else{
                openFoodProductRepository.delete(productOpenFood);
            }
        });
        return counter.get();
    }

}
