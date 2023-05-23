package kz.inflation.InflationApp.api;

import kz.inflation.InflationApp.dto.ProductCategoryDTO;
import kz.inflation.InflationApp.dto.ProductDTO;
import kz.inflation.InflationApp.models.ChangedProduct;
import kz.inflation.InflationApp.models.Product;
import kz.inflation.InflationApp.models.ProductCategory;
import kz.inflation.InflationApp.models.ProductInflation;
import kz.inflation.InflationApp.scripts.ProductsParser;
import kz.inflation.InflationApp.services.ChangedProductService;
import kz.inflation.InflationApp.services.ProductInflationService;
import kz.inflation.InflationApp.services.ProductService;
import org.apache.catalina.connector.Response;
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

    @Autowired
    public ProductAPIController(ProductService productService, ProductsParser productsParser, ProductInflationService productInflationService, ChangedProductService changedProductService) {
        this.productService = productService;
        this.productsParser = productsParser;
        this.productInflationService = productInflationService;
        this.changedProductService = changedProductService;
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

    @GetMapping("/products/changed-products/update")
    public ResponseEntity<List<ChangedProduct>> lastChangeUpdate(){
        List<List<Product>> list = productService.lastPriceChangeItems();
        List<ChangedProduct> changedProductList = new ArrayList<>();
        for (List<Product> products : list) {
            System.out.println(products);
            int priceChangeValue = products.get(0).getPrice() - products.get(1).getPrice();
            double priceChangePercent = (((double)products.get(0).getPrice() - products.get(1).getPrice()) / products.get(0).getPrice() * 100);
            ProductDTO productDTO = convertListToProductDTO(products).get(0);
            ChangedProduct changedProduct = new ChangedProduct(
                    Math.toIntExact(productDTO.getArticul()),
                    productDTO.getName(),
                    productDTO.getPrice(),
                    productDTO.getUpdatedTime(),
                    Math.toIntExact(productDTO.getCategory().getId()),
                    productDTO.getCategory().getName(),
                    priceChangeValue,
                    priceChangePercent
            );
            changedProductList.add(changedProduct);
        }
        changedProductService.resetTable();
        changedProductService.saveAllChangedProduct(changedProductList);
        return ResponseEntity.ok().body(changedProductService.getAll());
    }

    @GetMapping("/products/changed-products")
    public List<ChangedProduct> lastChange(){
        return changedProductService.getAll();
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
