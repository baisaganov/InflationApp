package kz.inflation.InflationApp.controllers;

import kz.inflation.InflationApp.models.householdGoods.HouseholdGoodsCategory;
import kz.inflation.InflationApp.services.householdGoodsServices.HouseholdGoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/household-goods")
public class HouseholdGoodsController {
    private final HouseholdGoodsCategoryService categoryService;
    private List<HouseholdGoodsCategory> categories;

    @Autowired
    public HouseholdGoodsController(HouseholdGoodsCategoryService categoryService) {
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
    public String getProduct(@PathVariable Long articul){
        return "products/product";
    }


}
