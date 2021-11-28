package com.barlipdev.dwyf.model.user;

import com.barlipdev.dwyf.model.product.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShoppingList {

    private String id;
    private String name;
    private List<Product> productList;

    public ShoppingList(){
        this.productList = new ArrayList<>();
        this.name = "Lista zakupów z dnia: "+LocalDate.now();
    }

    public ShoppingList(List<Product> productList) {
        this.productList = productList;
        this.name = "Lista zakupów z dnia: "+LocalDate.now();
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }


    public int GetNumberOfProducts(){
        return productList.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
