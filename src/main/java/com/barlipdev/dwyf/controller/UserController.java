package com.barlipdev.dwyf.controller;

import com.barlipdev.dwyf.model.User;
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
    public User add(@RequestBody User user){
        return userService.add(user);
    }

    @PostMapping("/users/product")
    public Product addProduct(@PathParam("userId") String userId, @RequestBody Product product){
        return userService.addProduct(userId,product);
    }

    @DeleteMapping("/users")
    public void delete(User user){
        userService.delete(user);
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

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping("/users/product/barcode")
    public Product addProductByBarCode(@PathParam("userId") String userId, @RequestBody Product product){
        return userService.scanProductWithBarCode(userId,product);
    }

}
