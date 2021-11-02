package com.barlipdev.dwyf.service;

import com.barlipdev.dwyf.mapper.Mapper;
import com.barlipdev.dwyf.model.user.ShoppingList;
import com.barlipdev.dwyf.model.user.User;
import com.barlipdev.dwyf.model.product.Product;
import com.barlipdev.dwyf.model.product.ProductOpenFood;
import com.barlipdev.dwyf.model.product.UsefulnessState;
import com.barlipdev.dwyf.repository.OpenFoodProductRepository;
import com.barlipdev.dwyf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OpenFoodProductRepository openFoodProductRepository;

    @Autowired
    Mapper mapper;


    public User findById(String id){
        return userRepository.findById(id).orElseThrow();
    }

    public List<User> add(User user){
        user.setAvatarUrl("http://51.68.139.166/img/default.png");
        user.setCreatedIn(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        userRepository.insert(user);
        return userRepository.findAll();
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public List<User> update(User user){
        userRepository.save(user);
        return userRepository.findAll();
    }

    public List<Product> getUserProducts(String id){
        return userRepository.findById(id).orElseThrow().getProductList();
    }

    public Product addProduct(String id, Product product){
        User user = userRepository.findById(id).orElseThrow();
        if (user != null) {
            if (user.getProductList() == null){
                user.setProductList(new ArrayList<>());
            }
            user.getProductList().add(product);
            userRepository.save(user);
            return product;
        }else{
            return null;
        }
    }

    public List<Product> getGoodQualityUserProducts(String id){
        User user = userRepository.findById(id).orElseThrow();
        List<Product> goodProducts = new ArrayList<>();
        LocalDate today = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        //searching expired products
        user.getProductList().forEach( userProduct -> {
            if (userProduct.getUsefulnessState() == UsefulnessState.CLOSEEXPIRYDATE){
                goodProducts.add(userProduct);
            }
        });

        user.getProductList().forEach(userProduct -> {
            if (userProduct.getUsefulnessState() == UsefulnessState.GOOD){
                goodProducts.add(userProduct);
            }
        });

        return goodProducts;
    }

    public Product scanProductWithBarCode(Product product){
        ProductOpenFood productOpenFood = openFoodProductRepository.findById(product.getId()).orElseThrow();
        return mapper.mapOpenFoodProductToProduct(productOpenFood, product);

    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow();
    }

    public User createNewShoppingList(String id,List<Product> productList){
        User user = userRepository.findById(id).orElseThrow();
        List<ShoppingList> userShoppingList = user.getShoppingLists();
        userShoppingList.add(new ShoppingList(productList));
        user.setShoppingLists(userShoppingList);

        return userRepository.save(user);
    }

    public List<ShoppingList> getShoppingLists(String id){
        User user = userRepository.findById(id).orElseThrow();
        return  user.getShoppingLists();
    }


}
