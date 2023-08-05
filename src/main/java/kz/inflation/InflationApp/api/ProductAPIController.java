package kz.inflation.InflationApp.api;

import kz.inflation.InflationApp.dto.products.ProductCategoryDTO;
import kz.inflation.InflationApp.dto.products.ProductDTO;
import kz.inflation.InflationApp.models.products.ChangedProduct;
import kz.inflation.InflationApp.models.products.Product;
import kz.inflation.InflationApp.models.products.ProductCategory;
import kz.inflation.InflationApp.models.products.ProductInflation;
import kz.inflation.InflationApp.scripts.ParsingStarter;
import kz.inflation.InflationApp.services.productServices.ChangedProductService;
import kz.inflation.InflationApp.services.productServices.ProductCategoryService;
import kz.inflation.InflationApp.services.productServices.ProductInflationService;
import kz.inflation.InflationApp.services.productServices.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProductAPIController {

    private final ProductService productService;
    private final ParsingStarter parsingStarter;
    private final ProductInflationService productInflationService;
    private final ChangedProductService changedProductService;
    private final ProductCategoryService categoryService;

    @Autowired
    public ProductAPIController(ProductService productService, ParsingStarter parsingStarter, ProductInflationService productInflationService, ChangedProductService changedProductService, ProductCategoryService categoryService) {
        this.productService = productService;
        this.parsingStarter = parsingStarter;
        this.productInflationService = productInflationService;
        this.changedProductService = changedProductService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products/update")
    public List<ProductDTO> updateProducts() throws InterruptedException {
        parsingStarter.singleThread();
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
                    convertToCategoryDTO((ProductCategory) e.getCategory())));
        }
        return dtoList;
    }

    private ProductCategoryDTO convertToCategoryDTO(ProductCategory category) {
        return new ProductCategoryDTO(category.getId(), category.getName());
    }

    private List<ProductCategoryDTO> convertToCategoryDTO(List<ProductCategory> categoryList) {
        List<ProductCategoryDTO> dtoList = new ArrayList<>();
        dtoList = categoryList.stream()
                .map(e -> new ProductCategoryDTO(e.getId(), e.getName()))
                .collect(Collectors.toList());
//        for (ProductCategory e : categoryList) {
//            dtoList.add(new ProductCategoryDTO(
//                    e.getId(),
//                    e.getName()
//            ));
//        }
        return dtoList;
    }


}
