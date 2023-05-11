package kz.inflation.InflationApp.api;

import kz.inflation.InflationApp.models.Product;
import kz.inflation.InflationApp.scripts.ProductsParser;
import kz.inflation.InflationApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductAPIController {

    private final ProductService productService;
    private final ProductsParser productsParser;

    @Autowired
    public ProductAPIController(ProductService productService, ProductsParser productsParser) {
        this.productService = productService;
        this.productsParser = productsParser;
    }

    @GetMapping("/products/update")
    public List<Product> updateProducts() {
        productsParser.startParsingProducts();
        return productService.getAllProducts();
    }

    @GetMapping("/products")
    public List<Product> products() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{articul}")
    public List<Product> getProductByArticul(@PathVariable Long articul) {
        return productService.getAllProductsByArticul(articul);
    }

    @GetMapping("/product/unique")
    public List<Product> getUniqueProducts(){
        return productService.getAllUniqueProducts();
    }
}
