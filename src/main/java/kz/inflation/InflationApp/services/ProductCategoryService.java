package kz.inflation.InflationApp.services;

import kz.inflation.InflationApp.models.ProductCategory;
import kz.inflation.InflationApp.repositories.ProductCategoryRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductCategoryService{

    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }


    public ProductCategory getCategory(String category) {
        return productCategoryRepository.findFirstByName(category);
    }
}