package kz.inflation.InflationApp.scripts;

import com.google.common.collect.Iterables;
import kz.inflation.InflationApp.models.Product;
import kz.inflation.InflationApp.services.ProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class ThreadParser implements Runnable {
    private final ProductService productService;
    private String link;
    private String threadName;

    @Autowired
    public ThreadParser(ProductService productService) {
        this.productService = productService;
    }

    public ThreadParser(ProductService productService, String link, String threadName) {
        this.productService = productService;
        this.threadName = threadName;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void seleniumGetLinks() {
        System.setProperty("webdriver.chrome.driver", "selenium/chromedriver");
//        System.setProperty("webdriver.chrome.driver", "selenium/chromedriver114");
        System.setProperty("webdriver.chrome.whitelistedIps", "");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
//        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);
        driver.get(this.link);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean switcher = true;

        do {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            WebElement first = null;
            WebElement second = Iterables.getLast(driver.findElement(By.className("pagination")).findElements(By.className("pagination__el")));

            if (!driver.findElement(By.className("pagination")).findElements(By.className("_disabled")).isEmpty()) {
                first = Iterables.getLast(driver.findElement(By.className("pagination")).findElements(By.className("_disabled")));
            }

            if(first != null && first.equals(second))
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
                    e.printStackTrace();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        } while (switcher);

        driver.quit();
        System.out.println(threadName + " is done working");

    }

    public void update(String seleniumDocument){
        Document document = Jsoup.parse(seleniumDocument);
        List<Product> productList = new ArrayList<>();

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

            String imageLink = product.getElementsByClass("item-card__image").attr("src");

//            System.out.println("Артикул: " + articul + "; " + name + "; " + price + " Image link: " + imageLink);
            Product product1 = new Product(articul, name, Integer.parseInt(price));
            productList.add(product1);
        }

        productService.saveAll(productList);

    }

    @Override
    public void run() {
        seleniumGetLinks();
    }
}
