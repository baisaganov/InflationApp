package kz.inflation.InflationApp.api;

import kz.inflation.InflationApp.dto.ProductCategoryDTO;
import kz.inflation.InflationApp.dto.ProductDTO;
import kz.inflation.InflationApp.models.Product;
import kz.inflation.InflationApp.models.ProductCategory;
import kz.inflation.InflationApp.models.ProductInflation;
import kz.inflation.InflationApp.scripts.ProductsParser;
import kz.inflation.InflationApp.services.ProductInflationService;
import kz.inflation.InflationApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductAPIController {

    private final ProductService productService;
    private final ProductsParser productsParser;
    private final ProductInflationService productInflationService;

    @Autowired
    public ProductAPIController(ProductService productService, ProductsParser productsParser, ProductInflationService productInflationService) {
        this.productService = productService;
        this.productsParser = productsParser;
        this.productInflationService = productInflationService;
    }

    @GetMapping("/products/update")
    public List<ProductDTO> updateProducts() {
//        productsParser.startParsingProducts();
        return convertListToProductDTO(productService.getAllProducts());
    }

    @GetMapping("/products")
    public List<ProductDTO> products() {
        return convertListToProductDTO(productService.getAllProducts());
    }

    @GetMapping("/products/{articul}")
    public List<ProductDTO> getProductByArticul(@PathVariable Long articul) {
        return convertListToProductDTO(productService.getAllProductsByArticul(articul));
    }

    @GetMapping("/products/unique")
    public List<ProductDTO> getUniqueProducts(@RequestParam(required = false, defaultValue = "0") int page,
                                              @RequestParam(required = false, defaultValue = "12") int size){
        return convertListToProductDTO(productService.getAllUniqueProducts(PageRequest.of(page, size)));
    }

    @GetMapping("/inflation")
    public List<ProductInflation> productInflations(){
        return productInflationService.getAllProducts();
    }


    private List<ProductDTO> convertListToProductDTO(List<Product> products) {
        List<ProductDTO> dtoList = new ArrayList<>();
        for (Product e : products) {
            dtoList.add(new ProductDTO(
                    e.getArticul(),
                    e.getName(),
                    e.getPrice(),
                    e.getUpdatedTime(),
                    convertToCategoryDTO(e.getCategory())));
        }
        return dtoList;
    }

    private ProductCategoryDTO convertToCategoryDTO(ProductCategory category) {
        return new ProductCategoryDTO(category.getId(), category.getName());
    }


}
