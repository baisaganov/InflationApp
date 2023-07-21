package kz.inflation.InflationApp.services.productServices;

import kz.inflation.InflationApp.models.products.Product;
import kz.inflation.InflationApp.models.products.ProductInflation;
import kz.inflation.InflationApp.repositories.productsRepository.ProductInflationRepository;
import kz.inflation.InflationApp.repositories.productsRepository.ProductRepository;
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
        List<Product> products = productRepository.getProductsByUpdatedTime(currentDate);
        System.out.println("today products in update inflation inform"+products.size());
        ProductInflation productInflation = this.updateInflationInformation(products, currentDate);
        productInflationRepository.save(productInflation);
    }

    public int todayProductsCount(){
        return productRepository.getProductsByUpdatedTime(LocalDate.now()).size();
    }


    private ProductInflation updateInflationInformation(List<Product> products, LocalDate date){
        Long count = (long) products.size();
        Long sum = 0L;
        for (Product product : products) {
            sum += (long) product.getPrice();
        }
        return new ProductInflation(count,
                sum,
                sum/count,
                date);
    }


}
