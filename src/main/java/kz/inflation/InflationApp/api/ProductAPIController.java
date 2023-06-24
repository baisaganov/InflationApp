package kz.inflation.InflationApp.api;

import kz.inflation.InflationApp.dto.ProductCategoryDTO;
import kz.inflation.InflationApp.dto.ProductDTO;
import kz.inflation.InflationApp.models.ChangedProduct;
import kz.inflation.InflationApp.models.Product;
import kz.inflation.InflationApp.models.ProductCategory;
import kz.inflation.InflationApp.models.ProductInflation;
import kz.inflation.InflationApp.scripts.ProductsParser;
import kz.inflation.InflationApp.services.ChangedProductService;
import kz.inflation.InflationApp.services.ProductCategoryService;
import kz.inflation.InflationApp.services.ProductInflationService;
import kz.inflation.InflationApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductAPIController {

    private final ProductService productService;
    private final ProductsParser productsParser;
    private final ProductInflationService productInflationService;
    private final ChangedProductService changedProductService;
    private final ProductCategoryService categoryService;

    @Autowired
    public ProductAPIController(ProductService productService, ProductsParser productsParser, ProductInflationService productInflationService, ChangedProductService changedProductService, ProductCategoryService categoryService) {
        this.productService = productService;
        this.productsParser = productsParser;
        this.productInflationService = productInflationService;
        this.changedProductService = changedProductService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products/update")
    public List<ProductDTO> updateProducts() throws InterruptedException {
        productsParser.singleThread();
        return convertListToProductDTO(productService.getAllProducts());
    }

    @GetMapping("/products")
    public List<ProductDTO> products() {
        return convertListToProductDTO(productService.getAllProducts());
    }

    @GetMapping("/products/not-updated3")
    public List<ProductDTO> updated3(){
        return convertListToProductDTO(productService.saveNotUpdatedItems());
    }



    @GetMapping("/products/{articul}")
    public List<ProductDTO> getProductByArticul(@PathVariable Long articul) {
        return convertListToProductDTO(productService.getAllProductsByArticul(articul));
    }

    @GetMapping("/products/unique")
    public List<ProductDTO> getUniqueProducts(@RequestParam(required = false, defaultValue = "0") int page,
                                              @RequestParam(required = false, defaultValue = "0", name="category") int category,
                                              @RequestParam(required = false, defaultValue = "12") int size
                                              ){
        return convertListToProductDTO(productService.getAllUniqueProducts(PageRequest.of(page, size), category));
    }

    @GetMapping("/inflation")
    public List<ProductInflation> productInflations(){
        return productInflationService.getAllProducts();
    }

    @GetMapping("/products/changed-products")
    public List<ChangedProduct> lastChange(){
        return changedProductService.getAll();
    }

    @GetMapping("/products/categories")
    public List<ProductCategoryDTO> categoryDTOList(){
        return convertToCategoryDTO(categoryService.findAll());
    }

    @GetMapping("/products/update-not-updated")
    public ResponseEntity<String> update(){
        productService.saveNotUpdatedItems();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/products/today-count")
    public int todayProductCount(){
        return productInflationService.todayProductsCount();
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

    private List<ProductCategoryDTO> convertToCategoryDTO(List<ProductCategory> categoryList) {
        List<ProductCategoryDTO> dtoList = new ArrayList<>();
        for (ProductCategory e : categoryList) {
            dtoList.add(new ProductCategoryDTO(
                    e.getId(),
                    e.getName()
            ));
        }
        return dtoList;
    }


}
