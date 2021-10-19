package com.barlipdev.dwyf.model.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "Products")
public class ProductOpenFood {

    @Id
    @Field("_id")
    private String id;
    @Field("_keywords")
    private List<String> tags;
    private String quantity;
    @Field("product_name")
    private String productName;
    private String brands;

    public ProductOpenFood(){

    }

    public ProductOpenFood(String _id, List<String> _keywords, String quantity, String product_name, String product_name_fr, String product_name_pl, String brands) {
        this.id = _id;
        this.tags = _keywords;
        this.quantity = quantity;
        this.productName = product_name;
        this.brands = brands;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrands() {
        return brands;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }

}
