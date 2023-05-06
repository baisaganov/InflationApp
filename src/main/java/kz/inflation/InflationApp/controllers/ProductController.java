package kz.inflation.InflationApp.controllers;

import kz.inflation.InflationApp.models.Product;
import kz.inflation.InflationApp.scripts.ProductsParser;
import kz.inflation.InflationApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;
    private final ProductsParser productsParser;

    @Autowired
    public ProductController(ProductService productService, ProductsParser productsParser) {
        this.productService = productService;
        this.productsParser = productsParser;
    }

    @GetMapping("/products")
    public List<Product> products(){
        productsParser.updateAllCategories();
        return productService.getAllProducts();
    }
}
