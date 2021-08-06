package com.barlipdev.dwyf.model.product;

public class ResponseOpenFood {

    private ProductOpenFood product;

    public ResponseOpenFood(){}

    public ResponseOpenFood(ProductOpenFood product) {
        this.product = product;
    }

    public ProductOpenFood getProduct() {
        return product;
    }

    public void setProduct(ProductOpenFood product) {
        this.product = product;
    }
}
