package com.barlipdev.dwyf.mapper;

import com.barlipdev.dwyf.model.product.Product;
import com.barlipdev.dwyf.model.product.ProductOpenFood;
import com.barlipdev.dwyf.model.product.ProductType;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

    public Product mapOpenFoodProductToProduct(ProductOpenFood productOpenFood, Product product){
        product.setId(productOpenFood.get_id());
        product.setName(productOpenFood.getProduct_name()+" "+productOpenFood.getBrands()+" "+productOpenFood.getQuantity());
        product.setSplittedProductTags(productOpenFood.get_keywords());

        String[] splittedCount = productOpenFood.getQuantity().split(" ");
        Double count = Double.parseDouble(splittedCount[0]);
        String type = splittedCount[1];

        if (count < 1.0 ){
            product.setCount(count * 0.001);
        }else {
            product.setCount(count);
        }
        if (type.equals("l") || type.equals("ml")){
            product.setProductType(ProductType.L);
        }else if (type.equals("kg") || type.equals("g")){
            product.setProductType(ProductType.KG);
        }

        return product;
    }

}
