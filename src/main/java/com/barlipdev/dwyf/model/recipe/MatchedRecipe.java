package com.barlipdev.dwyf.model.recipe;

import com.barlipdev.dwyf.model.recipe.Recipe;
import com.barlipdev.dwyf.model.product.Product;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MatchedRecipe {

    private Recipe recipe;
    private List<Product> availableProducts;
    private List<Product> notAvailableProducts;
    private List<RemovedProduct> removedProducts;

    public MatchedRecipe(){}

    public MatchedRecipe(Recipe recipe, List<Product> availableProducts, List<Product> notAvailableProducts, List<RemovedProduct> removedProducts) {
        this.recipe = recipe;
        this.availableProducts = availableProducts;
        this.notAvailableProducts = notAvailableProducts;
        this.removedProducts = removedProducts;
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

    public List<RemovedProduct> getRemovedProducts() {
        return removedProducts;
    }

    public void setRemovedProducts(List<RemovedProduct> removedProducts) {
        this.removedProducts = removedProducts;
    }
}
