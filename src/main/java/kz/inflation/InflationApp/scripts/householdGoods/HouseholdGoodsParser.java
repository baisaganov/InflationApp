package kz.inflation.InflationApp.scripts.householdGoods;


import kz.inflation.InflationApp.models.householdGoods.HouseholdGoods;
import kz.inflation.InflationApp.models.householdGoods.HouseholdGoodsCategory;
import kz.inflation.InflationApp.services.householdGoodsServices.HouseholdGoodsCategoryService;
import kz.inflation.InflationApp.services.householdGoodsServices.HouseholdGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component @Slf4j
public class HouseholdGoodsParser extends Thread {
    private final HouseholdGoodsService service;
    private final HouseholdGoodsCategoryService categoryService;

    private String link;
    private String threadName;
    private HouseholdGoodsCategory category;
    private List<String> linksList;

    @Autowired
    public HouseholdGoodsParser(HouseholdGoodsService service, HouseholdGoodsCategoryService categoryService) {
        this.service = service;
        this.categoryService = categoryService;
    }

    public HouseholdGoodsParser(HouseholdGoodsService service, HouseholdGoodsCategoryService categoryService, String link, String threadName) {
        this.service = service;
        this.categoryService = categoryService;
        this.threadName = threadName;
        this.link = link;
    }

    public HouseholdGoodsParser(HouseholdGoodsService service, HouseholdGoodsCategoryService categoryService, List<String> linksList) {
        this.service = service;
        this.categoryService = categoryService;
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

                boolean switcher = true;
                this.mobileVersion(driver, switcher);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        driver.quit();
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

        HashSet<HouseholdGoods> uniqueProducts = new HashSet<>(this.update(driver.getPageSource()));

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
        service.saveAll(uniqueProducts.stream().toList());
    }

    private List<HouseholdGoods> update(String seleniumDocument){
        Document document = Jsoup.parse(seleniumDocument);
        List<HouseholdGoods> productList = new ArrayList<>();
        if (category == null)
            category = categoryService.getCategory(document.getElementsByClass("catalog-grid-header__heading").text());
        Elements products = document.getElementsByClass("item-card");
        for (Element product : products) {
            long articul = Long.parseLong(product.attr("data-product-id"));
            String name = product.getElementsByClass("item-card__name").text();
            List<String> priceWithLiteral = Arrays.stream(product.getElementsByClass("item-card__debet").text().split(" ")).toList();// .getElementsByClass("item-card__prices-price")
//<div class="item-card__prices-group">
//    <div class="item-card__debet">
//        <div>
//            <span class="item-card__prices-price">325 ₸</span>
//        </div>
//    </div>
//    <div class="item-card__instalment">
//        <span class="item-card__prices-price">109 ₸</span>
//        <span class="item-card__add-info"> x3</span>
//    </div>
//</div>
            if (priceWithLiteral.size() < 2){
                priceWithLiteral = Arrays.stream(product.getElementsByClass("discount__price").text().split(" ")).toList();
            }
            int realPrice = Integer.parseInt(String.join("", priceWithLiteral.subList(0, priceWithLiteral.size()-1)));
            HouseholdGoods product1 = new HouseholdGoods(articul, name, realPrice);
            if (category == null || category.getId() == null) category = categoryService.getCategory(document.getElementsByClass("catalog-grid-header__heading").text());
//            if (category == null || category.getId() == null) continue;
            product1.setCategory(category);
            productList.add(product1);
        }
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
