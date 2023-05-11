package kz.inflation.InflationApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProductsController {

    @GetMapping()
    public String getProducts(){

        return "products/products";
    }

    @GetMapping("/{articul}")
    public String getProduct(@PathVariable Long articul){
        return "products/product";
    }
}
