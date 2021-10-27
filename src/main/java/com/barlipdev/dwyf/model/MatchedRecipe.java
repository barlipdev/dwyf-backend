package com.barlipdev.dwyf.model;

import com.barlipdev.dwyf.model.product.Product;

import java.util.HashMap;
import java.util.List;

public class MatchedRecipe {

    private Recipe recipe;
    private List<Product> availableProducts;
    private List<Product> notAvailableProducts;

    public MatchedRecipe(){}

    public MatchedRecipe(Recipe recipe, List<Product> availableProducts, List<Product> notAvailableProducts) {
        this.recipe = recipe;
        this.availableProducts = availableProducts;
        this.notAvailableProducts = notAvailableProducts;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public List<Product> getAvailableProducts() {
        return availableProducts;
    }

    public void setAvailableProducts(List<Product> availableProducts) {
        this.availableProducts = availableProducts;
    }

    public List<Product> getNotAvailableProducts() {
        return notAvailableProducts;
    }

    public void setNotAvailableProducts(List<Product> notAvailableProducts) {
        this.notAvailableProducts = notAvailableProducts;
    }

}
