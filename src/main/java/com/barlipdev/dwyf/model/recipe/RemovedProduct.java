package com.barlipdev.dwyf.model.recipe;

import com.barlipdev.dwyf.model.product.Product;

public class RemovedProduct {

    private String name;
    private Double count;

    public RemovedProduct(){

    }

    public RemovedProduct(String name, Double count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }
}
