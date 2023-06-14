package kz.inflation.InflationApp.services;

import kz.inflation.InflationApp.models.Product;
import kz.inflation.InflationApp.models.ProductInflation;
import kz.inflation.InflationApp.repositories.ProductInflationRepository;
import kz.inflation.InflationApp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductInflationService {
    private final ProductRepository productRepository;
    private final ProductInflationRepository productInflationRepository;

    @Autowired
    public ProductInflationService(ProductRepository productRepository, ProductInflationRepository productInflationRepository) {
        this.productRepository = productRepository;
        this.productInflationRepository = productInflationRepository;
    }

    public List<ProductInflation> getAllProducts(){
        return productInflationRepository.findAll();
    }

    @Transactional
    public void updateData(){
        LocalDate currentDate = LocalDate.now();
        Long count = productRepository.countAllByUpdatedTime(currentDate);
        Long sum = productRepository.sumAllByUpdatedTime(currentDate);
        ProductInflation productInflation = new ProductInflation(count,
                sum,
                sum/count,
                currentDate);
        productInflationRepository.save(productInflation);
    }


}
