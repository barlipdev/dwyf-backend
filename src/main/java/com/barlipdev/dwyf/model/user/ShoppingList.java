package com.barlipdev.dwyf.model.user;

import com.barlipdev.dwyf.model.product.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShoppingList {

    private List<Product> productList;
    private LocalDate createdOn;

    public ShoppingList(){
        this.productList = new ArrayList<>();
        this.createdOn = LocalDate.now();
    }

    public ShoppingList(List<Product> productList) {
        this.productList = productList;
        this.createdOn = LocalDate.now();
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public int GetNumberOfProducts(){
        return productList.size();
    }
}
