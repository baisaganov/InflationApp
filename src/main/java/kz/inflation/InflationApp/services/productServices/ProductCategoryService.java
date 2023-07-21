package kz.inflation.InflationApp.services.productServices;

import kz.inflation.InflationApp.models.products.ProductCategory;
import kz.inflation.InflationApp.repositories.productsRepository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }


    public ProductCategory getCategory(String category) {
        category = "%" + category + "%";
        return productCategoryRepository.findFirstByNameLike(category);
    }

    public List<ProductCategory> findAll(){
        return productCategoryRepository.findAll();
    }
}
