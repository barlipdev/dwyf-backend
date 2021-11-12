package com.barlipdev.dwyf.controller;

import com.barlipdev.dwyf.model.user.ShoppingList;
import com.barlipdev.dwyf.model.user.User;
import com.barlipdev.dwyf.model.product.Product;
import com.barlipdev.dwyf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/users")
    public List<User> add(@RequestBody User user){
        return userService.add(user);
    }

    @PostMapping("/users/product")
    public Product addProduct(@PathParam("userId") String userId, @RequestBody Product product){
        return userService.addProduct(userId,product);

    }

    @PostMapping("/users/update")
    public List<User> update(@RequestBody User user){
        return userService.update(user);
    }

    @DeleteMapping("/users")
    public void delete(User user){
        userService.delete(user);
    }

    @DeleteMapping("/users/product")
    public User deleteProduct(@RequestParam String userId, @RequestParam String productId){
        return userService.deleteProduct(userId,productId);
    }

    @DeleteMapping("/users/product/all")
    public User deleteExpiredProducts(@RequestParam String userId){
        return userService.deleteExpiredProducts(userId);
    }

    @GetMapping("/users/product")
    public List<Product> getUserProducts(@PathParam("userId") String userId){
        System.out.println(userId);
        return userService.getUserProducts(userId);
    }

    @GetMapping("/users/{id}")
    public User findById(@PathVariable String id){
        return userService.findById(id);
    }

    @GetMapping("/users/email")
    public User findByEmail(@RequestParam String email){
        return userService.findByEmail(email);
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping("/users/product/barcode")
    public Product addProductByBarCode(@RequestBody Product product,@RequestParam String date){
        if(date != null){
            product.setExpirationDate(date);
        }
        return userService.scanProductWithBarCode(product);
    }

    @PostMapping("/users/shoppingList")
    public User createNewShoppingList(@RequestParam String userId, @RequestBody ShoppingList shoppingList){
        return userService.createNewShoppingList(userId, shoppingList);
    }

    @GetMapping("/users/shoppingList")
    public List<ShoppingList> getShoppingLists(@RequestParam String userId){
        return userService.getShoppingLists(userId);
    }

}
