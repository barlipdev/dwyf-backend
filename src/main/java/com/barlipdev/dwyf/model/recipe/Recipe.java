package com.barlipdev.dwyf.model.recipe;

import com.barlipdev.dwyf.model.product.Product;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Recipes")
public class Recipe {

    @Id
    private String id;
    private String name;
    private List<Product> productList;
    private String description;
    private String foodType;

    public Recipe(){

    }

    public Recipe(String id, String name, List<Product> productList, String description, String foodType) {
        this.id = id;
        this.name = name;
        this.productList = productList;
        this.description = description;
        this.foodType = foodType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

}
