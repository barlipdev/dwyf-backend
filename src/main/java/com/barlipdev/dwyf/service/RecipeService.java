package com.barlipdev.dwyf.service;

import com.barlipdev.dwyf.exceptions.ResourceNotFoundException;
import com.barlipdev.dwyf.model.product.ProductFilter;
import com.barlipdev.dwyf.model.recipe.FoodTypeFilter;
import com.barlipdev.dwyf.model.recipe.MatchedRecipe;
import com.barlipdev.dwyf.model.recipe.Recipe;
import com.barlipdev.dwyf.model.recipe.RemovedProduct;
import com.barlipdev.dwyf.model.stats.PerformingRecipe;
import com.barlipdev.dwyf.model.user.User;
import com.barlipdev.dwyf.model.product.Product;
import com.barlipdev.dwyf.model.product.ProductType;
import com.barlipdev.dwyf.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PerformingRecipeService performingRecipeService;

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

    private List<Recipe> findRecipesByFoodType(FoodTypeFilter foodTypeFilter){
        String foodType = foodTypeFilter.toString().toLowerCase();
        String type = foodType.substring(0, 1).toUpperCase() + foodType.substring(1);

        return recipeRepository.findAllByFoodType(type).orElseThrow();
    }

    public List<MatchedRecipe> getPrefferedRecipe(String userId, ProductFilter productFilter, FoodTypeFilter foodTypeFilter){

        List<Recipe> recipeList = findRecipesByFoodType(foodTypeFilter);
        List<Product> goodQualityUserProducts = userService.getUserProducts(userId,productFilter);
        List<Product> goodProducts = new ArrayList<>();
        List<MatchedRecipe> matchedRecipeList = new ArrayList<>();
        List<Product> availableProducts;
        List<Product> notAvailableProducts;
        List<Product> usedRecipeProducts;
        List<RemovedProduct> removedProducts;
        HashMap<Recipe,Integer> prefferedRecipes = new HashMap<>();
        RemovedProduct removedProduct;

        //sorting products
        Collections.sort(goodQualityUserProducts);

        //getting recipes where goodQualityUserProducts exists
        for (Recipe recipe : recipeList){
            List<Product> recipeProducts = recipe.getProductList();
            int correctProductsCount = 0;
            availableProducts = new ArrayList<>();
            notAvailableProducts = new ArrayList<>();
            usedRecipeProducts = new ArrayList<>();
            removedProducts = new ArrayList<>();

            for (Product recipeProduct : recipeProducts){
                HashMap<Product,Integer> prefferedProduct = new HashMap<>();
                for (Product goodQualityProduct : goodQualityUserProducts){
                    int productPoints = checkTags(recipeProduct,goodQualityProduct);
                    if (productPoints > 0){
                        if (goodQualityProduct.getCount() >= recipeProduct.getCount()){
                            prefferedProduct.put(goodQualityProduct,productPoints);
                            if (!availableProducts.contains(goodQualityProduct)){
                                availableProducts.add(goodQualityProduct);
                                removedProduct = new RemovedProduct(goodQualityProduct.getName(),recipeProduct.getCount());
                                removedProducts.add(removedProduct);
                                if (!usedRecipeProducts.contains(recipeProduct)){
                                    usedRecipeProducts.add(recipeProduct);
                                }
                            }
                            break;
                        }
                    }
                }
                if (prefferedProduct.size() > 0){
                    Product finalProduct = getProductFromMap(prefferedProduct);
                    if(!goodProducts.contains(finalProduct)){
                        correctProductsCount = correctProductsCount + 1;
                        goodProducts.add(getProductFromMap(prefferedProduct));
                    }
                }
            }

            if (productFilter.equals(ProductFilter.CLOSEEXPIRED)  && correctProductsCount > 0){
                List<Product> additionalProducts = userService.getUserProducts(userId,ProductFilter.GOOD);

                for (Product recipeProduct : recipeProducts){
                    HashMap<Product,Integer> prefferedProduct = new HashMap<>();
                    for (Product additional : additionalProducts){
                        int productPoints = checkTags(recipeProduct,additional);
                        if (productPoints > 0 && !usedRecipeProducts.contains(recipeProduct)){
                            if (additional.getCount() >= recipeProduct.getCount()){
                                prefferedProduct.put(additional,productPoints);
                                if (!availableProducts.contains(additional)){
                                    availableProducts.add(additional);
                                    removedProduct = new RemovedProduct(additional.getName(),recipeProduct.getCount());
                                    removedProducts.add(removedProduct);
                                    if (!usedRecipeProducts.contains(recipeProduct)){
                                        usedRecipeProducts.add(recipeProduct);
                                    }
                                }
                                break;
                            }
                        }
                    }
                    if (prefferedProduct.size() > 0){
                        Product finalProduct = getProductFromMap(prefferedProduct);
                        if(!goodProducts.contains(finalProduct)){
                            correctProductsCount = correctProductsCount + 1;
                            goodProducts.add(getProductFromMap(prefferedProduct));
                        }
                    }
                }

            }

            for (Product recipeProduct : recipeProducts){
                if (!usedRecipeProducts.contains(recipeProduct)){
                    notAvailableProducts.add(recipeProduct);
                }
            }

            if (correctProductsCount > 0){
                prefferedRecipes.put(recipe,correctProductsCount);
                goodProducts.clear();
                MatchedRecipe matchedRecipe = new MatchedRecipe(recipe,availableProducts,notAvailableProducts,removedProducts);
                matchedRecipeList.add(matchedRecipe);
            }

        }
        List<MatchedRecipe> finalRecipes = new ArrayList<>();
        List<Recipe> sortedRecipes = getRecipesFromMap(prefferedRecipes);
        for (Recipe recipe : sortedRecipes){
            for(MatchedRecipe matchedRecipe : matchedRecipeList){
                if (matchedRecipe.getRecipe().getId().equals(recipe.getId())){
                    finalRecipes.add(matchedRecipe);
                }
            }
        }


        return finalRecipes;
    }


    private List<Recipe> getRecipesFromMap(HashMap<Recipe,Integer> recipesMap){
        HashMap<Recipe,Integer> sortedRecipeMap = sortByValue(recipesMap);
        List<Recipe> recipeList = new ArrayList<>();

        recipeList.addAll(sortedRecipeMap.keySet());
        return recipeList;
    }

    private HashMap<Recipe, Integer> sortByValue(HashMap<Recipe, Integer> hm)
    {
        List<Map.Entry<Recipe, Integer> > list =
                new LinkedList<Map.Entry<Recipe, Integer> >(hm.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Recipe, Integer> >() {
            public int compare(Map.Entry<Recipe, Integer> o1,
                               Map.Entry<Recipe, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        HashMap<Recipe, Integer> temp = new LinkedHashMap<Recipe, Integer>();
        for (Map.Entry<Recipe, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


    private Product getProductFromMap(HashMap<Product,Integer> productMap){
        int max = Collections.max(productMap.values());

        for(Map.Entry<Product, Integer> entry : productMap.entrySet()) {
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

    private Integer checkTags(Product recipeProduct, Product product){
        List<String> recipeProductTags = recipeProduct.getSplittedProductTags();
        List<String> productTags = product.getSplittedProductTags();
        int response = 0;

        for (String recipeProductTag : recipeProductTags){
            for (String productTag : productTags){

                if (recipeProductTag.toLowerCase().contains(productTag.toLowerCase())){
                    String tmp = recipeProductTag.replaceAll("\\s+","");
                    if (productTag.length() == tmp.length()){
                        response = response + 1;
                    }

                }
            }
        }
        return response;

    }

    public PerformingRecipe addPerform(String userId, MatchedRecipe matchedRecipe){
            return performingRecipeService.addPerformedRecipe(userId, matchedRecipe);
    }

}
