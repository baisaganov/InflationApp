package kz.inflation.InflationApp.controllers;

import jakarta.validation.constraints.Min;
import kz.inflation.InflationApp.models.Product;
import kz.inflation.InflationApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final ProductService productService;

    @Autowired
    public SearchController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String searchProduct(
            @RequestParam(required = true) String q,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "12") int size,
            Model model

            ) {

        model.addAttribute("req", q);
        model.addAttribute("page", page);
        model.addAttribute("maxPage", productService.searchCount(q)/size);

        List<Product> productList;

        try {
            int articul = Integer.parseInt(q);
            return "redirect:products/" + articul;
        } catch (NumberFormatException nfe){
            productList = productService.findByName(q, PageRequest.of(page, size));
        }

        if(productList.size()==1){
            return "redirect:products/"+productList.get(0).getArticul();
        } else if (productList.size()>1) {
            model.addAttribute("searchResult", productList);
        }
        return "search";
    }
}
