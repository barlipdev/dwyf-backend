package com.barlipdev.dwyf.model.product;


import java.time.LocalDate;
import java.util.List;

public class Product implements Comparable<Product>{
    private String id;
    private LocalDate expirationDate;
    private String name;
    private Double price;
    private Double count;
    private ProductType productType;
    private List<String> splittedProductTags;

    private String productTags;

    public Product(){

    }

    public Product(String id, String expirationDate, String name, Double price, Double count, ProductType productType, String productTag) {
        this.id = id;
        this.expirationDate = LocalDate.parse(expirationDate);
        this.name = name;
        this.price = price;
        this.count = count;
        this.productType = productType;
        this.productTags = productTag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = LocalDate.parse(expirationDate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public List<String> getSplittedProductTags() {
        return splittedProductTags;
    }

    public void setSplittedProductTags(List<String> splittedProductTags) {
        this.splittedProductTags = splittedProductTags;
    }

    public String getProductTags() {
        return productTags;
    }

    public void setProductTags(String productTags) {
        this.productTags = productTags;
    }

    @Override
    public int compareTo(Product o) {
        return this.expirationDate.compareTo(o.getExpirationDate());
    }
}
