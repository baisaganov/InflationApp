package kz.inflation.InflationApp.controllers;

import kz.inflation.InflationApp.models.pharmacy.PharmacyCategory;
import kz.inflation.InflationApp.models.products.ProductCategory;
import kz.inflation.InflationApp.services.pharmacyServices.PharmacyCategoryService;
import kz.inflation.InflationApp.services.productServices.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pharmacy")
public class PharmacyController {
    private final PharmacyCategoryService categoryService;
    private List<PharmacyCategory> categories;

    @Autowired
    public PharmacyController(PharmacyCategoryService categoryService) {
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
        model.addAttribute("itemType", "pharmacy");
        return "products/product";
    }


}
