package kz.inflation.InflationApp.scripts;

import kz.inflation.InflationApp.services.ProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class ProductsParser {

    private final ProductService productService;

    @Autowired
    public ProductsParser(ProductService productService) {
        this.productService = productService;
    }

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
    public void updateAllCategories(){
        String url = "https://kaspi.kz/shop/nur-sultan/c/freeze%20dried%20food%20on%20a%20camping%20trip/?q=%3Acategory%3AFreeze%20dried%20food%20on%20a%20camping%20trip&sort=relevance&sc=";

        try {
//            Document document = Jsoup.connect(url)
//                    .userAgent("Mozilla")
//                    .timeout(5000)
//                    .referrer("https://google.com")
//                    .get();
            Document document = Jsoup.parse(new URL(url), 10000);

            Elements products = document.getElementsByClass("item-card");
            for (Element product : products) {
                Long articul = Long.parseLong(product.attr("data-product-id").toString());
                String name = product.getElementsByClass("item-card__info").text();
//                String price = product.getElementsByClass("item-card__debet").text();

//                String price = product.select("span[class=item-card__prices-price]").text();
                System.out.println(articul + " " + name + " " );
//                System.out.println(product);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
