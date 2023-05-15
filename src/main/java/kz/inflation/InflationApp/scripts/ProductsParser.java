package kz.inflation.InflationApp.scripts;

import kz.inflation.InflationApp.services.ProductInflationService;
import kz.inflation.InflationApp.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


/*
    TODO: Поставить таймер на выполнение потока
*/
@Component
public class ProductsParser {

    private final ProductService productService;
    private final ProductInflationService productInflationService;

    @Autowired
    public ProductsParser(ProductService productService, ProductInflationService productInflationService) {
        this.productService = productService;
        this.productInflationService = productInflationService;
    }
    @Scheduled(initialDelay = 10000, fixedDelay = 1000 * 60 * 60 )
    public void startParsingProducts(){
        ExecutorService service = Executors.newFixedThreadPool(1);

        String milkLink = "https://kaspi.kz/shop/nur-sultan/c/dairy%20and%20eggs/?q=%3Acategory%3ADairy%20and%20eggs%3AallMerchants%3AMagnum&sort=relevance&sc=";
        String candies = "https://kaspi.kz/shop/nur-sultan/c/pastry/?q=%3Acategory%3APastry%3AallMerchants%3AMagnum&sort=relevance&sc=";
        String fruitsAndVeg = "https://kaspi.kz/shop/nur-sultan/c/fruits%20and%20vegetables/?q=%3Acategory%3AFruits%20and%20vegetables%3AallMerchants%3AMagnum&sort=relevance&sc=";
        String juicesAndWater = "https://kaspi.kz/shop/nur-sultan/c/water%20and%20beverages/?q=%3Acategory%3AWater%20and%20beverages%3AallMerchants%3AMagnum&sort=relevance&sc=";
        String pasta = "https://kaspi.kz/shop/nur-sultan/c/grains%20and%20pasta/?q=%3Acategory%3AGrains%20and%20pasta%3AallMerchants%3AMagnum&sort=relevance&sc=";
        String oilsAndSauces = "https://kaspi.kz/shop/nur-sultan/c/spices%20and%20seasoning/?q=%3Acategory%3ASpices%20and%20seasoning%3AallMerchants%3AMagnum&sort=relevance&sc=";
        String bread = "https://kaspi.kz/shop/nur-sultan/c/bread%20and%20bakery/?q=%3Acategory%3ABread%20and%20bakery%3AallMerchants%3AMagnum&sort=relevance&sc=";
        String sugarAndSpices = "https://kaspi.kz/shop/nur-sultan/c/sugar%20salt%20spices/?q=%3Acategory%3ASugar%20salt%20spices%3AallMerchants%3AMagnum&sort=relevance&sc=";
        String cannedGoods = "https://kaspi.kz/shop/nur-sultan/c/canned%20goods/?q=%3Acategory%3ACanned%20goods%3AallMerchants%3AMagnum&sort=relevance&sc=";
        String meatAndPoultry = "https://kaspi.kz/shop/nur-sultan/c/meat%20and%20poultry/?q=%3Acategory%3AMeat%20and%20poultry%3AallMerchants%3AMagnum&sort=relevance&sc=";
        String sausages = "https://kaspi.kz/shop/nur-sultan/c/sausages%20and%20meat%20delicacies/?q=%3Acategory%3ASausages%20and%20meat%20delicacies%3AallMerchants%3AMagnum&sort=relevance&sc=";
        String teaAndCoffee = "https://kaspi.kz/shop/nur-sultan/c/tea%20and%20coffee/?q=%3Acategory%3ATea%20and%20coffee%3AallMerchants%3AMagnum&sort=relevance&sc=";
        String chipsAndNuts = "https://kaspi.kz/shop/nur-sultan/c/chips%20and%20nuts/?q=%3Acategory%3AChips%20and%20nuts%3AallMerchants%3AMagnum&sort=relevance&sc=";
        String baking = "https://kaspi.kz/shop/nur-sultan/c/everything%20for%20baking/?q=%3Acategory%3AEverything%20for%20baking%3AallMerchants%3AMagnum&sort=relevance&sc=";
        String frozenFoods = "https://kaspi.kz/shop/nur-sultan/c/frozen%20foods/?q=%3Acategory%3AFrozen%20foods%3AallMerchants%3AMagnum&sort=relevance&sc=";
        String readyMeal = "https://kaspi.kz/shop/nur-sultan/c/ready%20meal/?q=%3Acategory%3AReady%20meal%3AallMerchants%3AMagnum&sort=relevance&sc=";
        String seafood = "https://kaspi.kz/shop/nur-sultan/c/seafood/?q=%3Acategory%3ASeafood%3AallMerchants%3AMagnum&sort=relevance&sc=";
        List<Future<?>> futureList = new ArrayList<>();

        List<ThreadParser> parserList = new ArrayList<ThreadParser>(
                List.of(
                        new ThreadParser(productService, milkLink, "milks"),
                        new ThreadParser(productService, candies,"candies"),
                        new ThreadParser(productService, fruitsAndVeg, "fruitsAndVeg"),
                        new ThreadParser(productService, juicesAndWater, "juicesAndWater"),
                        new ThreadParser(productService, oilsAndSauces, "oilsAndSauces"),
                        new ThreadParser(productService, pasta, "pasta"),
                        new ThreadParser(productService, bread, "bread"),
                        new ThreadParser(productService, sugarAndSpices, "sugarAndSpices"),
                        new ThreadParser(productService, cannedGoods, "cannedGoods"),
                        new ThreadParser(productService, meatAndPoultry, "meatAndPoultry"),
                        new ThreadParser(productService, sausages, "sausages"),
                        new ThreadParser(productService, teaAndCoffee, "teaAndCoffee"),
                        new ThreadParser(productService, chipsAndNuts, "chipsAndNuts"),
                        new ThreadParser(productService, baking, "baking"),
                        new ThreadParser(productService, frozenFoods, "frozenFoods"),
                        new ThreadParser(productService, readyMeal, "readyMeal"),
                        new ThreadParser(productService, seafood, "seafood")
                )
        );

        try {
            for (ThreadParser parser: parserList) {
                futureList.add(service.submit(parser));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        for (Future<?> future : futureList) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        service.shutdown();
        try {
            service.awaitTermination(2, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        productService.saveNotUpdatedItems();
        productInflationService.updateData();


    }
}
