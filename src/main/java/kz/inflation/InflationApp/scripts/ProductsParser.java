package kz.inflation.InflationApp.scripts;

import kz.inflation.InflationApp.services.ProductInflationService;
import kz.inflation.InflationApp.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/*
    TODO: Сделать многопоточный запуск
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
        ExecutorService service = Executors.newFixedThreadPool(6);

//        String milkLink = "https://kaspi.kz/shop/nur-sultan/c/dairy%20and%20eggs/?q=%3Acategory%3ADairy%20and%20eggs%3AallMerchants%3AMagnum&sort=relevance&sc=";
//        String candies = "https://kaspi.kz/shop/nur-sultan/c/pastry/?q=%3Acategory%3APastry%3AallMerchants%3AMagnum&sort=relevance&sc=";
        String fruitsAndVeg = "https://kaspi.kz/shop/nur-sultan/c/fruits%20and%20vegetables/?q=%3Acategory%3AFruits%20and%20vegetables%3AallMerchants%3AMagnum&sort=relevance&sc=";
        String juicesAndWater = "https://kaspi.kz/shop/nur-sultan/c/water%20and%20beverages/?q=%3Acategory%3AWater%20and%20beverages%3AallMerchants%3AMagnum&sort=relevance&sc=";
//        String pasta = "https://kaspi.kz/shop/nur-sultan/c/grains%20and%20pasta/?q=%3Acategory%3AGrains%20and%20pasta%3AallMerchants%3AMagnum&sort=relevance&sc=";
//        String oilsAndSauces = "https://kaspi.kz/shop/nur-sultan/c/spices%20and%20seasoning/?q=%3Acategory%3ASpices%20and%20seasoning%3AallMerchants%3AMagnum&sort=relevance&sc=";
        List<Future<?>> futureList = new ArrayList<>();
        List<ThreadParser> parserList = new ArrayList<ThreadParser>(
                List.of(
//                        new ThreadParser(productService, milkLink, "milks"),
//                        new ThreadParser(productService, candies,"candies"),
                        new ThreadParser(productService, fruitsAndVeg, "fruitsAndVeg"),
                        new ThreadParser(productService, juicesAndWater, "juicesAndWater")
//                        new ThreadParser(productService, pasta, "pasta"),
//                        new ThreadParser(productService, oilsAndSauces, "oilsAndSauces")
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
        productInflationService.updateData();




    }
}
