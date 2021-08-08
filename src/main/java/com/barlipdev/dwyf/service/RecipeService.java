package com.barlipdev.dwyf.service;

import com.barlipdev.dwyf.exceptions.ResourceNotFoundException;
import com.barlipdev.dwyf.model.Recipe;
import com.barlipdev.dwyf.model.product.Product;
import com.barlipdev.dwyf.model.product.ProductType;
import com.barlipdev.dwyf.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserService userService;

    public Recipe add(Recipe recipe){
        List<Product> recipeProducts;
        recipeProducts = recipe.getProductList();

        recipeProducts.forEach(product -> {

            product.setSplittedProductTags(splitProductTag(product.getProductTag()));

            if (product.getProductType() == ProductType.SZT){
                if (product.getCount() >= 1.0){
                    double count = product.getCount();
                    product.setName(product.getName()+" "+(int)count+" szt");
                }else if(product.getCount() == 0.25){
                    product.setName(product.getName()+ " 1/4 szt");
                }else if(product.getCount() == 0.5){
                    product.setName(product.getName()+ " 1/2 szt");
                }else if(product.getCount() == 0.75){
                    product.setName(product.getName()+ " 3/4 szt");
                }
            }else {
                if (product.getCount() < 0.100){
                    if (product.getCount() < 0.015){
                        double count = product.getCount() / 0.005;
                        product.setName(product.getName()+" "+(int)count+"x łyżeczka");
                    }else {
                        double count = product.getCount() / 0.015;
                        product.setName(product.getName() + " " + (int) count + "x łyżka");
                    }
                }else if (product.getCount() >= 0.1 && product.getCount() < 1.0){
                    if (product.getProductType() == ProductType.KG){
                        double count = product.getCount() * 1000;
                        product.setName(product.getName()+" "+(int)count+" g");
                    }
                    if (product.getProductType() == ProductType.L){
                        double count = product.getCount() * 1000;
                        product.setName(product.getName()+" "+(int)count+" ml");
                    }
                }else if(product.getCount() >= 1.0){
                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
                    if (product.getProductType() == ProductType.KG){
                        product.setName(product.getName()+" "+decimalFormat.format(product.getCount())+" kg");
                    }
                    if (product.getProductType() == ProductType.L){
                        product.setName(product.getName()+" "+decimalFormat.format(product.getCount())+" L");
                    }
                }
            }
        });
        return recipeRepository.insert(recipe);
    }

    public Recipe save(Recipe recipe){
        return recipeRepository.save(recipe);
    }

    public Recipe findById(String id){
        return recipeRepository.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Recipe "+id+" not found!"));
    }

    public void delete(Recipe recipe){
        recipeRepository.delete(recipe);
    }

    public List<Product> getProductsList(String recipeId){
        return findById(recipeId).getProductList();
    }

    public List<Recipe> findAll(){
        return recipeRepository.findAll();
    }

    public Recipe getPrefferedRecipe(String userId){
        List<Recipe> recipeList = recipeRepository.findAll();
        List<Product> expiredProducts = userService.getExpiredProducts(userId);
        HashMap<Recipe,Integer> prefferedRecipes = new HashMap<>();
        LocalDate today = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        //sorting products
        Collections.sort(expiredProducts);

        //getting recipes where expiredProducts exists
        recipeList.forEach(recipe -> {
            List<Product> recipeProducts = recipe.getProductList();
            AtomicInteger correctProductsCount = new AtomicInteger();
            correctProductsCount.set(0);

            recipeProducts.forEach(recipeProduct ->{
                expiredProducts.forEach(expiredProduct -> {
                    if (recipeProduct.getSplittedProductTags().contains(expiredProduct.getSplittedProductTags())){
                        if (Period.between(expiredProduct.getExpirationDate(),today).getDays() > 0){
                            correctProductsCount.set(correctProductsCount.intValue() + Period.between(expiredProduct.getExpirationDate(),today).getDays());
                        }
                    }
                });
            });
            if (correctProductsCount.get() > 0){
                prefferedRecipes.put(recipe,correctProductsCount.get());
                System.out.println("Recipe points: " + recipe.getName() +" "+ correctProductsCount.intValue() );
                correctProductsCount.set(0);
            }
        });
        return getRecipeFromMap(prefferedRecipes);
    }

    private Recipe getRecipeFromMap(HashMap<Recipe,Integer> recipesMap){
        int max = Collections.max(recipesMap.values());

        for(Map.Entry<Recipe, Integer> entry : recipesMap.entrySet()) {
            if (entry.getValue() == max){
                return entry.getKey();
            }
        }
        return null;
    }

    public List<Integer> getRecipesCountByTypes(){
        String[] typesOfRecipes = {"Sniadanie","Obiad","Deser","Kolacja","Inne"};
        List<Integer> result = new ArrayList<>();
        List<Recipe> recipeList = recipeRepository.findAll();

        for (int i=0; i< typesOfRecipes.length; i++){
            int index = i;
            AtomicInteger recipesCount = new AtomicInteger();
            recipeList.forEach(recipe -> {
                if (recipe.getFoodType().equals(typesOfRecipes[index])){
                    recipesCount.getAndIncrement();
                }
            });
            result.add(recipesCount.intValue());
        }
        return result;
    }

    private List<String> splitProductTag(String productTag){
        List<String> splittedTags = new ArrayList<>();
        String[] temp = productTag.split(",");
        for (String tag : temp){
            splittedTags.add(tag);
        }
        return splittedTags;
    }

}
