package kz.inflation.InflationApp.scripts;

import kz.inflation.InflationApp.dto.pharmacies.PharmacyCategoryDTO;
import kz.inflation.InflationApp.dto.pharmacies.PharmacyDTO;
import kz.inflation.InflationApp.dto.products.ProductCategoryDTO;
import kz.inflation.InflationApp.dto.products.ProductDTO;
import kz.inflation.InflationApp.models.pharmacy.ChangedPharmacyAbstract;
import kz.inflation.InflationApp.models.pharmacy.Pharmacy;
import kz.inflation.InflationApp.models.pharmacy.PharmacyCategory;
import kz.inflation.InflationApp.models.products.ChangedProduct;
import kz.inflation.InflationApp.models.products.Product;
import kz.inflation.InflationApp.models.products.ProductCategory;
import kz.inflation.InflationApp.scripts.pharmaciesParse.PharmacyParser;
import kz.inflation.InflationApp.scripts.productsParse.ProductParser;
import kz.inflation.InflationApp.services.pharmacyServices.ChangedPharmacyService;
import kz.inflation.InflationApp.services.pharmacyServices.PharmacyCategoryService;
import kz.inflation.InflationApp.services.pharmacyServices.PharmacyInflationService;
import kz.inflation.InflationApp.services.pharmacyServices.PharmacyService;
import kz.inflation.InflationApp.services.productServices.ChangedProductService;
import kz.inflation.InflationApp.services.productServices.ProductCategoryService;
import kz.inflation.InflationApp.services.productServices.ProductInflationService;
import kz.inflation.InflationApp.services.productServices.ProductService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/*
    TODO: Поставить таймер на выполнение потока
*/
@Component @Slf4j
public class ParsingStarter {

    private final ProductService productService;
    private final ProductInflationService productInflationService;
    private final ProductCategoryService productCategoryService;
    private final ChangedProductService changedProductService;

    private final PharmacyService pharmacyService;
    private final PharmacyInflationService pharmacyInflationService;
    private final PharmacyCategoryService pharmacyCategoryService;
    private final ChangedPharmacyService changedPharmacyService;


    @Autowired
    public ParsingStarter(ProductService productService, ProductInflationService productInflationService, ProductCategoryService productCategoryService, ChangedProductService changedProductService, PharmacyService pharmacyService, PharmacyInflationService pharmacyInflationService, PharmacyCategoryService pharmacyCategoryService, ChangedPharmacyService changedPharmacyService) {
        this.productService = productService;
        this.productInflationService = productInflationService;
        this.productCategoryService = productCategoryService;
        this.changedProductService = changedProductService;

        this.pharmacyService = pharmacyService;
        this.pharmacyInflationService = pharmacyInflationService;
        this.pharmacyCategoryService = pharmacyCategoryService;
        this.changedPharmacyService = changedPharmacyService;
    }

/*
* Шедулер с еженедельным запуском по четвергам
* */
//    @Scheduled(cron = "0 16 17 * * *", zone = "GMT+6")
    @Scheduled(cron = "0 0 8 * * THU", zone = "GMT+6")
    public void singleThread() throws InterruptedException {
        // Запуск парсера продуктов
        this.productParserStarter();

        // Запуск парсера лекарств
        this.pharmacyParserStarter();

        // Запуск парсера хоз. товаров
//        this.householdGoodsParserStarter();
    }

    private void productParserStarter() throws InterruptedException {
        if(!this.productParser()) log.info("Product parser had Errors");

        log.info("Updating not parsed products");
        long start = System.currentTimeMillis();

        productService.saveNotUpdatedItems();

        log.info("Update done in " + (System.currentTimeMillis()-start));
        start = System.currentTimeMillis();
        log.info("Updating inflation information");

        productInflationService.updateData();

        log.info("Update done in " + (System.currentTimeMillis()-start));

        this.lastChangeProductUpdate();
    }

    private boolean productParser() throws InterruptedException {
        long parsingProductsTimeStart = System.currentTimeMillis();
        log.info("Parsing products started");
        log.info(System.getProperty("user.dir"));
        List<String> list = new ArrayList<>();
        list.add("https://kaspi.kz/shop/nur-sultan/c/dairy%20and%20eggs/?q=%3Acategory%3ADairy%20and%20eggs%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("https://kaspi.kz/shop/nur-sultan/c/pastry/?q=%3Acategory%3APastry%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("https://kaspi.kz/shop/nur-sultan/c/fruits%20and%20vegetables/?q=%3Acategory%3AFruits%20and%20vegetables%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("https://kaspi.kz/shop/nur-sultan/c/water%20and%20beverages/?q=%3Acategory%3AWater%20and%20beverages%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("https://kaspi.kz/shop/nur-sultan/c/grains%20and%20pasta/?q=%3Acategory%3AGrains%20and%20pasta%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("https://kaspi.kz/shop/nur-sultan/c/spices%20and%20seasoning/?q=%3Acategory%3ASpices%20and%20seasoning%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("https://kaspi.kz/shop/nur-sultan/c/bread%20and%20bakery/?q=%3Acategory%3ABread%20and%20bakery%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("https://kaspi.kz/shop/nur-sultan/c/sugar%20salt%20spices/?q=%3Acategory%3ASugar%20salt%20spices%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("https://kaspi.kz/shop/nur-sultan/c/canned%20goods/?q=%3Acategory%3ACanned%20goods%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("https://kaspi.kz/shop/nur-sultan/c/meat%20and%20poultry/?q=%3Acategory%3AMeat%20and%20poultry%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("https://kaspi.kz/shop/nur-sultan/c/sausages%20and%20meat%20delicacies/?q=%3Acategory%3ASausages%20and%20meat%20delicacies%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("https://kaspi.kz/shop/nur-sultan/c/tea%20and%20coffee/?q=%3Acategory%3ATea%20and%20coffee%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("https://kaspi.kz/shop/nur-sultan/c/chips%20and%20nuts/?q=%3Acategory%3AChips%20and%20nuts%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("https://kaspi.kz/shop/nur-sultan/c/everything%20for%20baking/?q=%3Acategory%3AEverything%20for%20baking%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("https://kaspi.kz/shop/nur-sultan/c/frozen%20foods/?q=%3Acategory%3AFrozen%20foods%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("https://kaspi.kz/shop/nur-sultan/c/ready%20meal/?q=%3Acategory%3AReady%20meal%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("https://kaspi.kz/shop/nur-sultan/c/seafood/?q=%3Acategory%3ASeafood%3AallMerchants%3AMagnum&sort=relevance&sc=");
        list.add("https://kaspi.kz/shop/nur-sultan/c/seafood/?q=%3Acategory%3ASeafood%3AallMerchants%3AMagnum&sort=relevance&sc=");
        Thread thread1 = new Thread(new ProductParser(productService, productCategoryService, list.subList(0, 6)));
        Thread thread2 = new Thread(new ProductParser(productService, productCategoryService, list.subList(6, 13)));
        Thread thread3 = new Thread(new ProductParser(productService, productCategoryService, list.subList(13, 17)));

        try {
            thread1.start();
        } catch (Exception e){
            log.error("Thread1 error" + e.getLocalizedMessage());
            return false;
        }

        try {
            thread2.start();
        } catch (Exception e){
            log.error("Thread2 error" + e.getLocalizedMessage());
            return false;

        }
        try {
            thread3.start();
        } catch (Exception e){
            log.error("Thread3 error" + e.getLocalizedMessage());
            return false;
        }

        thread1.join();
        thread2.join();
        thread3.join();

        log.info("Parsing done in:" + (((System.currentTimeMillis() - parsingProductsTimeStart)/60)/60/10) + " minutes");
        return true;
    }

    private void pharmacyParserStarter() throws InterruptedException {
        if(!this.pharmacyParser()) log.info("Pharmacy parser had Errors");

        log.info("Updating not parsed pharmacy");
        long start = System.currentTimeMillis();

        pharmacyService.saveNotUpdatedItems();

        log.info("Update done in " + (System.currentTimeMillis()-start));
        start = System.currentTimeMillis();
        log.info("Updating inflation information");

        pharmacyInflationService.updateData();

        log.info("Update done in " + (System.currentTimeMillis()-start));

        this.lastChangePharmacyUpdate();
    }


    private boolean pharmacyParser() throws InterruptedException {
        long parsingProductsTimeStart = System.currentTimeMillis();
        log.info("Parsing pharmacy started");
        List<String> list = new ArrayList<>();
        list.add("https://kaspi.kz/shop/nur-sultan/c/medications/?q=%3Acategory%3AMedications&sort=relevance&sc=");
        list.add("https://kaspi.kz/shop/nur-sultan/c/vitamins/?q=%3Acategory%3AVitamins&sort=relevance&sc=");
        list.add("https://kaspi.kz/shop/nur-sultan/c/phytoteas%20and%20herbal%20preparations/?q=%3Acategory%3APhytoteas%20and%20herbal%20preparations&sort=relevance&sc=");
        Thread thread1 = new Thread(new PharmacyParser(pharmacyService, pharmacyCategoryService, list.subList(0, 1)));
        Thread thread2 = new Thread(new PharmacyParser(pharmacyService, pharmacyCategoryService, list.subList(1, 2)));
        Thread thread3 = new Thread(new PharmacyParser(pharmacyService, pharmacyCategoryService, list.subList(2, 3)));

        try {
            thread1.start();
        } catch (Exception e){
            log.error("Pharmacy Thread1 error" + e.getLocalizedMessage());
            return false;
        }

        try {
            thread2.start();
        } catch (Exception e){
            log.error("Pharmacy Thread2 error" + e.getLocalizedMessage());
            return false;

        }
        try {
            thread3.start();
        } catch (Exception e){
            log.error("Pharmacy Thread3 error" + e.getLocalizedMessage());
            return false;
        }

        thread1.join();
        thread2.join();
        thread3.join();

        log.info("Pharmacy Parsing done in:" + (((System.currentTimeMillis() - parsingProductsTimeStart)/60)/60/10) + " minutes");
        return true;
    }

    /*
    * TODO:
    *       Сделать скрипт под хоз товары
    * */
    private boolean householdGoodsParserStarter() throws InterruptedException {
        long parsingProductsTimeStart = System.currentTimeMillis();
        log.info("Parsing products started");
        log.info(System.getProperty("user.dir"));
        List<String> list = new ArrayList<>();
        Thread thread1 = new Thread(new ProductParser(productService, productCategoryService, list.subList(0, 6)));
        Thread thread2 = new Thread(new ProductParser(productService, productCategoryService, list.subList(6, 13)));
        Thread thread3 = new Thread(new ProductParser(productService, productCategoryService, list.subList(13, 17)));

        try {
            thread1.start();
        } catch (Exception e){
            log.error("Thread1 error" + e.getLocalizedMessage());
            return false;
        }

        try {
            thread2.start();
        } catch (Exception e){
            log.error("Thread2 error" + e.getLocalizedMessage());
            return false;

        }
        try {
            thread3.start();
        } catch (Exception e){
            log.error("Thread3 error" + e.getLocalizedMessage());
            return false;
        }

        thread1.join();
        thread2.join();
        thread3.join();

        log.info("Parsing done in:" + (((System.currentTimeMillis() - parsingProductsTimeStart)/60)/60/10) + " minutes");
        return true;
    }

    private void lastChangeProductUpdate(){
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

    private void lastChangePharmacyUpdate(){
        log.info("Updating changed pharmacy list");
        long start = System.currentTimeMillis();
        List<List<Pharmacy>> list = pharmacyService.lastPriceChangeItems();
        List<ChangedPharmacyAbstract> changedProductList = new ArrayList<>();
        for (List<Pharmacy> products : list) {
            int priceChangeValue = products.get(0).getPrice() - products.get(1).getPrice();
            double priceChangePercent = Math.abs(100 - ((double)products.get(0).getPrice() / products.get(1).getPrice()) * 100);
            PharmacyDTO pharmacyDTO = convertListToPharmacyDTO(products).get(0);
            ChangedPharmacyAbstract changedProduct = new ChangedPharmacyAbstract(
                    Math.toIntExact(pharmacyDTO.getArticul()),
                    pharmacyDTO.getName(),
                    pharmacyDTO.getPrice(),
                    pharmacyDTO.getUpdatedTime(),
                    Math.toIntExact(pharmacyDTO.getCategory().getId()),
                    pharmacyDTO.getCategory().getName(),
                    priceChangeValue,
                    priceChangePercent
            );
            changedProductList.add(changedProduct);
        }
        changedPharmacyService.resetTable();
        changedPharmacyService.saveAllChangedPharmacy(changedProductList);
        log.info("Update done in " + (System.currentTimeMillis()-start));

    }

    private List<PharmacyDTO> convertListToPharmacyDTO(List<Pharmacy> products) {
        List<PharmacyDTO> dtoList = new ArrayList<>();
        for (Pharmacy e : products) {
            dtoList.add(new PharmacyDTO(
                    e.getArticul(),
                    e.getName(),
                    e.getPrice(),
                    e.getUpdatedTime(),
                    convertToPharmacyCategoryDTO(e.getCategory())));
        }
        return dtoList;
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

    private PharmacyCategoryDTO convertToPharmacyCategoryDTO(PharmacyCategory category) {
        return new PharmacyCategoryDTO(category.getId(), category.getName());
    }
}