package kz.inflation.InflationApp.scripts.productsParse;

import com.google.common.collect.Iterables;
import kz.inflation.InflationApp.models.products.Product;
import kz.inflation.InflationApp.models.products.ProductCategory;
import kz.inflation.InflationApp.services.productServices.ProductCategoryService;
import kz.inflation.InflationApp.services.productServices.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


@Component @Slf4j
public class ProductParser extends Thread {
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    private String link;
    private String threadName;
    private ProductCategory category;
    private List<String> linksList;

    @Autowired
    public ProductParser(ProductService productService, ProductCategoryService productCategoryService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
    }

    public ProductParser(ProductService productService, ProductCategoryService productCategoryService, String link, String threadName) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
        this.threadName = threadName;
        this.link = link;
    }

    public ProductParser(ProductService productService, ProductCategoryService productCategoryService, List<String> linksList) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
        this.linksList = linksList;
    }

    public String getLink() {
        return link;
    }

    public void seleniumGetLinks() {
        var user_agent = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) " +
                "AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16";
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-headless");
        options.addPreference("general.useragent.override", "Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1");
        options.addPreference("devtools.responsive.userAgent.override", "Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1");

        options.setLogLevel(FirefoxDriverLogLevel.ERROR);
        WebDriver driver = new FirefoxDriver(options);
        driver.manage().window().setSize(new Dimension(500, 812));


        for (String link : linksList) {

            try {
                this.category = null;
                driver.get(link);

                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                String fileName = System.currentTimeMillis() + "Test";
//                File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//                File destFile = new File(System.getProperty("user.dir") + "/" + fileName );
//                try {
//                    FileHandler.copy(screenshotFile, destFile);
//                } catch (IOException e){
//                    e.printStackTrace();
//                }
                boolean switcher = true;
                this.mobileVersion(driver, switcher);
//                boolean isMobile = Jsoup.parse(driver.getPageSource())
//                        .getElementsByClass("search-result__title-notfound")
//                        .text()
//                        .equals("К сожалению, мы ничего не нашли по Вашему запросу.");
//
//                if (isMobile) mobileVersion(driver, switcher);
//                else desktopVersion(driver, switcher);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        driver.quit();
    }

    /*Парсинг с десктопной версии сайта*/
    private void desktopVersion(WebDriver driver, boolean switcher) {
        try {
            do {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                this.update(driver.getPageSource());

                WebElement disabledPaginationButton = null;
                WebElement nextButton = null;
                try {
                    nextButton = Iterables.getLast(driver.findElement(By.className("pagination")).findElements(By.className("pagination__el")));
                } catch (NoSuchElementException noElement) {
                    driver.navigate().refresh();
                    System.out.println("nextButton element not founded in " + link);
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (!driver.findElement(By.className("pagination")).findElements(By.className("_disabled")).isEmpty()) {
                    disabledPaginationButton = Iterables.getLast(driver.findElement(By.className("pagination")).findElements(By.className("_disabled")));
                }

                if (disabledPaginationButton != null && disabledPaginationButton.equals(nextButton))
                    switcher = false;


                try {
                    Iterables.getLast(driver.findElement(By.className("pagination")).findElements(By.className("pagination__el"))).click();
                    Thread.sleep(10000);
                } catch (ElementClickInterceptedException e) {
                    System.out.println("Ошибка в клике");
                    driver.navigate().refresh();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        Thread.sleep(30000);
                        Iterables.getLast(driver.findElement(By.className("pagination")).findElements(By.className("pagination__el"))).click();
                    } catch (ElementClickInterceptedException e) {
                        System.out.println("Повторная ошибка в клике");
                        System.out.println(driver.getCurrentUrl());
                        String[] nextPage = driver.getCurrentUrl().split("=");
                        if (nextPage.length - 1 > 1)
                            nextPage[nextPage.length - 1] = String.valueOf(Integer.parseInt(nextPage[nextPage.length - 1]) + 1);
                        driver.get(String.join("=", nextPage));
                        System.out.println(String.join("=", nextPage));
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } while (switcher);

            log.info(this.category.getName() + " is done working");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Caught error in Single thread loop: " + e.getLocalizedMessage());
        }
    }

    /*Парсинг мобильной версии сайта*/
    private void mobileVersion(WebDriver driver, boolean switcher){
        if(Jsoup.parse(driver.getPageSource())
                .getElementsByClass("topbar__heading")
                .text().equals("Выберите Ваш Город")){
            driver.findElement(By.cssSelector("a[data-city-id='710000000']")).click();
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        driver.navigate().refresh();

        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int lastElement = Integer.parseInt(Jsoup.parse(driver.getPageSource())
                .getElementsByClass("item-card")
                .last()
                .attr("data-product-id")
        );

        HashSet<Product> uniqueProducts = new HashSet<>(this.update(driver.getPageSource()));

        do {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

            try {
                Thread.sleep(1333);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int endElement = Integer.parseInt(Jsoup.parse(driver.getPageSource())
                    .getElementsByClass("item-card")
                    .last()
                    .attr("data-product-id")
                    );

            uniqueProducts.addAll(this.update(driver.getPageSource()));

            if (lastElement == endElement) {
                int retryEndElement = Integer.parseInt(Jsoup.parse(driver.getPageSource())
                        .getElementsByClass("item-card")
                        .last()
                        .attr("data-product-id")
                );
                if (lastElement == retryEndElement) switcher = false;
                else lastElement = retryEndElement;
            } else lastElement = endElement;
        } while (switcher);
        productService.saveAll(uniqueProducts.stream().toList());
    }

    private List<Product> update(String seleniumDocument){
        Document document = Jsoup.parse(seleniumDocument);
        List<Product> productList = new ArrayList<>();
        if (category == null)
            category = productCategoryService.getCategory(document.getElementsByClass("catalog-grid-header__heading").text());
        Elements products = document.getElementsByClass("item-card");
        for (Element product : products) {
            long articul = Long.parseLong(product.attr("data-product-id").toString());
            String name = product.getElementsByClass("item-card__name").text();
            List<String> priceWithLiteral = Arrays.stream(product.getElementsByClass("item-card__prices-price").text().split(" ")).toList();
            if (priceWithLiteral.size() < 2){
                priceWithLiteral = Arrays.stream(product.getElementsByClass("discount__price").text().split(" ")).toList();
            }
            int realPrice = Integer.parseInt(String.join("", priceWithLiteral.subList(0, priceWithLiteral.size()-1)));
            Product product1 = new Product(articul, name, realPrice);
            if (category == null || category.getId() == null) category = (ProductCategory) productService.getProductByArticul(product1.getArticul()).getCategory();
            product1.setCategory(category);
            productList.add(product1);
        }
//        productService.saveAll(productList);
        return productList;
    }


    @Override
    public void run() {
        seleniumGetLinks();
    }

    public String getThreadName() {
        return threadName;
    }
}
