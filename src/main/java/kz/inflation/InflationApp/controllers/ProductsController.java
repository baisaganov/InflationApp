package kz.inflation.InflationApp.controllers;

import kz.inflation.InflationApp.dto.products.ProductCategoryDTO;
import kz.inflation.InflationApp.models.products.ProductCategory;
import kz.inflation.InflationApp.services.productServices.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {
    private final ProductCategoryService categoryService;
    private List<ProductCategory> categories;

    @Autowired
    public ProductsController(ProductCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public String getProducts(Model model){
        if (categories == null) categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("className", categories.get(0).getClass().getName());
        return "products/products";
    }

    @GetMapping("/{articul}")
    public String getProduct(@PathVariable Long articul, Model model){
        model.addAttribute("itemType", "product");
        return "products/product";
    }


}
