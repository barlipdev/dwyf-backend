package com.barlipdev.dwyf.model;

import com.barlipdev.dwyf.model.product.Product;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "Users")
public class User {

    @Id
    private String id;
    private String avatarUrl;
    private String username;
    private String email;
    private String password;
    private LocalDate createdIn;
    private UserRole userRole;
    private List<Product> productList;

    public User(){

    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.setProductList(new ArrayList<>());
    }

    public User(String username, String email, String password, List<Product> productList) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.productList = productList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public LocalDate getCreatedIn() {
        return createdIn;
    }

    public void setCreatedIn(LocalDate createdIn) {
        this.createdIn = createdIn;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
