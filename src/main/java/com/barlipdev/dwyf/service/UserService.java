package com.barlipdev.dwyf.service;

import com.barlipdev.dwyf.mapper.Mapper;
import com.barlipdev.dwyf.model.User;
import com.barlipdev.dwyf.model.product.Product;
import com.barlipdev.dwyf.model.product.ResponseOpenFood;
import com.barlipdev.dwyf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    Mapper mapper;

    public User findById(String id){
        return userRepository.findById(id).orElseThrow();
    }

    public User add(User user){
        return userRepository.insert(user);
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void update(User user){
        userRepository.save(user);
    }

    public List<Product> getUserProducts(String id){
        return userRepository.findById(id).orElseThrow().getProductList();
    }

    public List<Product> addProduct(String id, Product product){
        User user = userRepository.findById(id).orElseThrow();
        if (user != null) {
            if (user.getProductList() == null){
                user.setProductList(new ArrayList<>());
            }
            user.getProductList().add(product);
            userRepository.save(user);
            return user.getProductList();
        }else{
            return null;
        }
    }

    public List<Product> getExpiredProducts(String id){
        User user = userRepository.findById(id).orElseThrow();
        List<Product> expiredProducts = new ArrayList<>();
        LocalDate today = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        //searching expired products
        user.getProductList().forEach( userProduct -> {
            if (Period.between(today,userProduct.getExpirationDate()).getDays() <= 2){
                expiredProducts.add(userProduct);
            }
        });

        return expiredProducts;
    }

    public Product addProductWithBarCode(String userId, Product product){
        RestTemplate connection = new RestTemplate();
        final String uri = "https://world.openfoodfacts.org/api/v0/product/"+product.getId();

        ResponseOpenFood response = connection.getForObject(uri, ResponseOpenFood.class);
        //addProduct(userId,mapper.mapOpenFoodProductToProduct(response.getProduct(), product));
        return mapper.mapOpenFoodProductToProduct(response.getProduct(), product);

    }

}
