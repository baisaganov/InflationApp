package kz.inflation.InflationApp.services;

import kz.inflation.InflationApp.models.Product;
import kz.inflation.InflationApp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAllByArticul(Long articul){
        return repository.findProductsByArticul(articul);
    }

    public void saveAll(List<Product> products){
        repository.saveAll(products);
    }

    public List<Product> getAllProducts(){
        return repository.findAll();
    }
}
