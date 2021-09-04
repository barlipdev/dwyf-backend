package com.barlipdev.dwyf.mapper;

import com.barlipdev.dwyf.model.product.Product;
import com.barlipdev.dwyf.model.product.ProductOpenFood;
import com.barlipdev.dwyf.model.product.ProductType;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

    public Product mapOpenFoodProductToProduct(ProductOpenFood productOpenFood, Product product){
        product.setId(productOpenFood.get_id());
        if (productOpenFood.getProduct_name_pl() != null ){
            product.setName(productOpenFood.getProduct_name_pl()+" "+productOpenFood.getBrands()+" "+productOpenFood.getQuantity());
        }else{
            product.setName(productOpenFood.getProduct_name()+" "+productOpenFood.getBrands()+" "+productOpenFood.getQuantity());
        }

        product.setSplittedProductTags(productOpenFood.get_keywords());

        String[] splittedCount = productOpenFood.getQuantity().split(" ");
        Double count = Double.parseDouble(splittedCount[0]);
        String type = splittedCount[1];


        if (type.equals("l") || type.equals("ml")){
            if (type.equals("l")){
                product.setCount(count);
                product.setProductType(ProductType.L);
            }else {
                product.setCount(count);
                product.setProductType(ProductType.ml);
            }
        }else if (type.equals("kg") || type.equals("g")){
            if (type.equals("kg")){
                product.setCount(count);
                product.setProductType(ProductType.KG);
            }else {
                product.setCount(count);
                product.setProductType(ProductType.g);
            }
        }else if (type.contains("szt")){
            product.setCount(count);
            product.setProductType(ProductType.SZT);
        }

        return product;
    }

}
