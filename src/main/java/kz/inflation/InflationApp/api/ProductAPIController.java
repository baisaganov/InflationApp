package kz.inflation.InflationApp.api;

import kz.inflation.InflationApp.models.Product;
import kz.inflation.InflationApp.scripts.ProductsParser;
import kz.inflation.InflationApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/products/unique")
    public List<Product> getUniqueProducts(@RequestParam(required = false, defaultValue = "0") int page,
                                           @RequestParam(required = false, defaultValue = "12") int size){
        return productService.getAllUniqueProducts(PageRequest.of(page, size));
    }
}
