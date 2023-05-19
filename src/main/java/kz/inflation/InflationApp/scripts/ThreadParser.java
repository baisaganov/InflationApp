package kz.inflation.InflationApp.scripts;

import com.google.common.collect.Iterables;
import kz.inflation.InflationApp.models.Product;
import kz.inflation.InflationApp.services.ProductCategoryService;
import kz.inflation.InflationApp.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component @Slf4j
public class ThreadParser implements Runnable {
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    private String link;
    private String threadName;

    @Autowired
    public ThreadParser(ProductService productService, ProductCategoryService productCategoryService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
    }

    public ThreadParser(ProductService productService, ProductCategoryService productCategoryService, String link, String threadName) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
        this.threadName = threadName;
        this.link = link;

    }

    public String getLink() {
        return link;
    }

    public void seleniumGetLinks() {
        // CHROME DRIVER
//        System.setProperty("webdriver.chrome.driver", "selenium/chromedriver");
//        System.setProperty("webdriver.chrome.whitelistedIps", "");
//
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--remote-allow-origins=*");
////        options.addArguments("--headless");
//
//        WebDriver driver = new ChromeDriver(options);


//        FIREFOX DRIVER
//        System.setProperty("webdriver.gecko.driver", "selenium/geckodriver"); // MAC OS
//        System.setProperty("webdriver.gecko.driver", "selenium/geckodriver"); // UBUNTU
//        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver"); // UBUNTU
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-headless");
        options.addPreference("--log", "error");
        WebDriver driver = null;
//        try {
//            driver = new RemoteWebDriver(new URL("https://www.google.com/"), options);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }

        driver = new FirefoxDriver(options);


        driver.get(this.link);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean switcher = true;

        do {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            WebElement disabledPaginationButton = null;
            WebElement nextButton = null;
            try {
                nextButton = Iterables.getLast(driver.findElement(By.className("pagination")).findElements(By.className("pagination__el")));

            } catch (NoSuchElementException noElement){
                driver.navigate().refresh();
                System.out.println("nextButton element not founded in " + threadName);
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            if (!driver.findElement(By.className("pagination")).findElements(By.className("_disabled")).isEmpty()) {
                disabledPaginationButton = Iterables.getLast(driver.findElement(By.className("pagination")).findElements(By.className("_disabled")));
            }

            if(disabledPaginationButton != null && disabledPaginationButton.equals(nextButton))
                switcher = false;

            update(driver.getPageSource());

            try {
                Iterables.getLast(driver.findElement(By.className("pagination")).findElements(By.className("pagination__el"))).click();
                Thread.sleep(10000);
            } catch (ElementClickInterceptedException e) {
                System.out.println("Ошибка в клике");
                driver.navigate().refresh();
            } catch (InterruptedException e){
                e.printStackTrace();
            } finally {
                try {
                    Thread.sleep(30000);
                    Iterables.getLast(driver.findElement(By.className("pagination")).findElements(By.className("pagination__el"))).click();
                } catch (ElementClickInterceptedException e) {
                    System.out.println("Повторная ошибка в клике");
                    System.out.println(driver.getCurrentUrl());
                    String[] nextPage = driver.getCurrentUrl().split("=");
                    if (nextPage.length-1 > 1)
                        nextPage[nextPage.length-1]= String.valueOf(Integer.parseInt(nextPage[nextPage.length-1])+1);
                    driver.get(String.join("=", nextPage));
                    System.out.println(Arrays.toString(nextPage));
                    e.printStackTrace();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        } while (switcher);

        driver.quit();
        log.info(threadName + " thread is done working");
    }

    public void update(String seleniumDocument){
        Document document = Jsoup.parse(seleniumDocument);
        List<Product> productList = new ArrayList<>();
        String category = document.getElementsByClass("breadcrumbs__item_disabled").text();

        Elements products = document.getElementsByClass("item-card");
        for (Element product : products) {
            long articul = Long.parseLong(product.attr("data-product-id").toString());
            String[] nameWithReviews = product.getElementsByClass("item-card__info").text()
                    .split("Цена")[0]
                    .split(" ");

            String name = String.join(" ", Arrays.copyOfRange(nameWithReviews,
                    0,
                    nameWithReviews.length-2));

            String price = product.getElementsByClass("item-card__debet").text()
                    .split("Цена")[1]
                    .replaceAll("[^0-9]", "");

//            String imageLink = product.getElementsByClass("item-card__image").attr("src");
//            System.out.println("Артикул: " + articul + "; " + name + "; " + price + " Image link: " + imageLink);
            Product product1 = new Product(articul, name, Integer.parseInt(price));
            product1.setCategory(productCategoryService.getCategory(category));
            productList.add(product1);
        }

        productService.saveAll(productList);

    }

    @Override
    public void run() {
        seleniumGetLinks();
    }

    public String getThreadName() {
        return threadName;
    }
}
