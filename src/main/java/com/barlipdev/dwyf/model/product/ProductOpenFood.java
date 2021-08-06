package com.barlipdev.dwyf.model.product;

import java.util.List;

public class ProductOpenFood {

    private String _id;
    private List<String> _keywords;
    private String quantity;
    private String product_name;
    private String brands;

    public ProductOpenFood(){

    }

    public ProductOpenFood(String _id, List<String> _keywords, String quantity, String product_name, String brands) {
        this._id = _id;
        this._keywords = _keywords;
        this.quantity = quantity;
        this.product_name = product_name;
        this.brands = brands;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> get_keywords() {
        return _keywords;
    }

    public void set_keywords(List<String> _keywords) {
        this._keywords = _keywords;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getBrands() {
        return brands;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }
}
