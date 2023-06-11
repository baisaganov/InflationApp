package kz.inflation.InflationApp.scripts;

import kz.inflation.InflationApp.dto.ProductCategoryDTO;
import kz.inflation.InflationApp.dto.ProductDTO;
import kz.inflation.InflationApp.models.ChangedProduct;
import kz.inflation.InflationApp.models.Product;
import kz.inflation.InflationApp.models.ProductCategory;
import kz.inflation.InflationApp.services.ChangedProductService;
import kz.inflation.InflationApp.services.ProductCategoryService;
import kz.inflation.InflationApp.services.ProductInflationService;
import kz.inflation.InflationApp.services.ProductService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/*
    TODO: Поставить таймер на выполнение потока
*/
@Component @Slf4j
public class ProductsParser {

    private final ProductService productService;
    private final ProductInflationService productInflationService;
    private final ProductCategoryService productCategoryService;
    private final ChangedProductService changedProductService;


    @Autowired
    public ProductsParser(ProductService productService, ProductInflationService productInflationService, ProductCategoryService productCategoryService, ChangedProductService changedProductService) {
        this.productService = productService;
        this.productInflationService = productInflationService;
        this.productCategoryService = productCategoryService;
        this.changedProductService = changedProductService;
    }


    @Scheduled(initialDelay = 1000*60*60*12, fixedDelay = 1000*60*60*24)
    public void singleThread() throws InterruptedException {
        long parsingProductsTimeStart = System.currentTimeMillis();
        log.info("Parsing products started");
        log.info(System.getProperty("user.dir"));
        List<String> list = new ArrayList<>();
        list.add("http://kaspi.kz/shop/nur-sultan/c/dairy%20and%20eggs/?q=%3Acategory%3ADairy%20and%20eggs%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("http://kaspi.kz/shop/nur-sultan/c/pastry/?q=%3Acategory%3APastry%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("http://kaspi.kz/shop/nur-sultan/c/fruits%20and%20vegetables/?q=%3Acategory%3AFruits%20and%20vegetables%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("http://kaspi.kz/shop/nur-sultan/c/water%20and%20beverages/?q=%3Acategory%3AWater%20and%20beverages%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("http://kaspi.kz/shop/nur-sultan/c/grains%20and%20pasta/?q=%3Acategory%3AGrains%20and%20pasta%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("http://kaspi.kz/shop/nur-sultan/c/spices%20and%20seasoning/?q=%3Acategory%3ASpices%20and%20seasoning%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("http://kaspi.kz/shop/nur-sultan/c/bread%20and%20bakery/?q=%3Acategory%3ABread%20and%20bakery%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("http://kaspi.kz/shop/nur-sultan/c/sugar%20salt%20spices/?q=%3Acategory%3ASugar%20salt%20spices%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("http://kaspi.kz/shop/nur-sultan/c/canned%20goods/?q=%3Acategory%3ACanned%20goods%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("http://kaspi.kz/shop/nur-sultan/c/meat%20and%20poultry/?q=%3Acategory%3AMeat%20and%20poultry%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("http://kaspi.kz/shop/nur-sultan/c/sausages%20and%20meat%20delicacies/?q=%3Acategory%3ASausages%20and%20meat%20delicacies%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("http://kaspi.kz/shop/nur-sultan/c/tea%20and%20coffee/?q=%3Acategory%3ATea%20and%20coffee%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("http://kaspi.kz/shop/nur-sultan/c/chips%20and%20nuts/?q=%3Acategory%3AChips%20and%20nuts%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("http://kaspi.kz/shop/nur-sultan/c/everything%20for%20baking/?q=%3Acategory%3AEverything%20for%20baking%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("http://kaspi.kz/shop/nur-sultan/c/frozen%20foods/?q=%3Acategory%3AFrozen%20foods%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("http://kaspi.kz/shop/nur-sultan/c/ready%20meal/?q=%3Acategory%3AReady%20meal%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("http://kaspi.kz/shop/nur-sultan/c/seafood/?q=%3Acategory%3ASeafood%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("http://kaspi.kz/shop/nur-sultan/c/seafood/?q=%3Acategory%3ASeafood%3AallMerchants%3AMagnum&sort=relevance&sc=");
        Thread thread1 = new Thread(new ThreadParser(productService, productCategoryService, list.subList(0, 6)));
        Thread thread2 = new Thread(new ThreadParser(productService, productCategoryService, list.subList(6, 13)));
        Thread thread3 = new Thread(new ThreadParser(productService, productCategoryService, list.subList(13, 17)));

        try {
            thread1.start();
        } catch (Exception e){
            log.error("Thread1 error" + e.getLocalizedMessage());
        }

        try {
            thread2.start();
        } catch (Exception e){
            log.error("Thread2 error" + e.getLocalizedMessage());
        }

        try {
            thread3.start();
        } catch (Exception e){
            log.error("Thread3 error" + e.getLocalizedMessage());
        }
        thread1.join();
        thread2.join();
        thread3.join();

        log.info("Parsing done in:" + (((System.currentTimeMillis() - parsingProductsTimeStart)/60)/60) + " minutes");

        log.info("Updating not parsed products");
        long start = System.currentTimeMillis();
        productService.saveNotUpdatedItems2();
        log.info("Update done in " + (System.currentTimeMillis()-start));

        start = System.currentTimeMillis();
        log.info("Updating inflation information");
        productInflationService.updateData();
        log.info("Update done in " + (System.currentTimeMillis()-start));

        this.lastChangeUpdate();
    }

    private void lastChangeUpdate(){
        log.info("Updating changed product list");
        long start = System.currentTimeMillis();
        List<List<Product>> list = productService.lastPriceChangeItems();
        List<ChangedProduct> changedProductList = new ArrayList<>();
        for (List<Product> products : list) {
            int priceChangeValue = products.get(0).getPrice() - products.get(1).getPrice();
            double priceChangePercent = Math.abs(100 - ((double)products.get(0).getPrice() / products.get(1).getPrice()) * 100);
            ProductDTO productDTO = convertListToProductDTO(products).get(0);
            ChangedProduct changedProduct = new ChangedProduct(
                    Math.toIntExact(productDTO.getArticul()),
                    productDTO.getName(),
                    productDTO.getPrice(),
                    productDTO.getUpdatedTime(),
                    Math.toIntExact(productDTO.getCategory().getId()),
                    productDTO.getCategory().getName(),
                    priceChangeValue,
                    priceChangePercent
            );
            changedProductList.add(changedProduct);
        }
        changedProductService.resetTable();
        changedProductService.saveAllChangedProduct(changedProductList);
        log.info("Update done in " + (System.currentTimeMillis()-start));

    }

    private List<ProductDTO> convertListToProductDTO(List<Product> products) {
        List<ProductDTO> dtoList = new ArrayList<>();
        for (Product e : products) {
            dtoList.add(new ProductDTO(
                    e.getArticul(),
                    e.getName(),
                    e.getPrice(),
                    e.getUpdatedTime(),
                    convertToCategoryDTO(e.getCategory())));
        }
        return dtoList;
    }

    private ProductCategoryDTO convertToCategoryDTO(ProductCategory category) {
        return new ProductCategoryDTO(category.getId(), category.getName());
    }
}
