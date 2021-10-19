package com.barlipdev.dwyf.controller;

import com.barlipdev.dwyf.model.product.Product;
import com.barlipdev.dwyf.model.product.ProductOpenFood;
import com.barlipdev.dwyf.repository.OpenFoodProductRepository;
import com.barlipdev.dwyf.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OpenFoodProductController {

    @Autowired
    ProductsService productsService;

    @GetMapping("/products")
    public Page<ProductOpenFood> getAllProducts(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size){
        return productsService.getProducts(page,size);
    }

    @GetMapping("/products/{id}")
    public ProductOpenFood getProductById(@PathVariable String id){
        return productsService.getProductById(id);
    }

    @GetMapping("/products/count")
    public int getProductsOkCount(){
        return productsService.getCountOkProducts();
    }

    @GetMapping("/products/byName")
    public Page<ProductOpenFood> getProductsByName(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam String name){
        return productsService.getProductsByName(page, size, name);
    }


}
