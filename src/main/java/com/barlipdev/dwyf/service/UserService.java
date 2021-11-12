package com.barlipdev.dwyf.service;

import com.barlipdev.dwyf.mapper.Mapper;
import com.barlipdev.dwyf.model.product.ProductFilter;
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

    public User deleteProduct(String userId,String productId){
        User user = userRepository.findById(userId).orElseThrow();

        user.getProductList().removeIf(product -> product.getId().equals(productId));

        return  userRepository.save(user);
    }

    public User deleteExpiredProducts(String userId){
        User user = userRepository.findById(userId).orElseThrow();

        user.getProductList().removeIf(product -> product.getUsefulnessState() == UsefulnessState.EXPIRED);

        return userRepository.save(user);
    }

    public List<Product> getUserProducts(String id, ProductFilter productFilter){
        User user = userRepository.findById(id).orElseThrow();
        List<Product> products = new ArrayList<>();

        if (productFilter.equals(ProductFilter.ALL)){
            products = user.getProductList();
        }else if(productFilter.equals(ProductFilter.GOOD)){
            products = getUserProductsByUsefulnessState(user.getProductList(),UsefulnessState.GOOD);
        }else if(productFilter.equals(ProductFilter.CLOSEEXPIRED)){
            products = getUserProductsByUsefulnessState(user.getProductList(),UsefulnessState.CLOSEEXPIRYDATE);
        }

        return products;
    }

    private List<Product> getUserProductsByUsefulnessState(List<Product> userProducts, UsefulnessState usefulnessState){
        List<Product> products = new ArrayList<>();

        for (Product product : userProducts){
            if (product.getUsefulnessState() == usefulnessState){
                products.add(product);
            }
        }

        return products;
    }

    public Product scanProductWithBarCode(Product product){
        ProductOpenFood productOpenFood = openFoodProductRepository.findById(product.getId()).orElseThrow();
        return mapper.mapOpenFoodProductToProduct(productOpenFood, product);

    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow();
    }

    public User createNewShoppingList(String id,ShoppingList shoppingList){
        User user = userRepository.findById(id).orElseThrow();
        List<ShoppingList> userShoppingList;

        if (user.getShoppingLists() == null){
            userShoppingList = new ArrayList<>();
            user.setShoppingLists(userShoppingList);
        }else {
            userShoppingList = user.getShoppingLists();
        }

        shoppingList.setName("Lista zakupów z dnia: "+LocalDate.now());
        userShoppingList.add(shoppingList);
        user.setShoppingLists(userShoppingList);

        return userRepository.save(user);
    }

    public List<ShoppingList> getShoppingLists(String id){
        User user = userRepository.findById(id).orElseThrow();
        List<ShoppingList> userShoppingList;

        if (user.getShoppingLists() == null){
            userShoppingList = new ArrayList<>();
            return userShoppingList;
        }else {
            userShoppingList = user.getShoppingLists();
        }

        return  userShoppingList;
    }


}
