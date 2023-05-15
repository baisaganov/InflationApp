package kz.inflation.InflationApp.services;

import kz.inflation.InflationApp.models.Product;
import kz.inflation.InflationApp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAllByArticul(Long articul){
        return repository.findProductsByArticulOrderByUpdatedTimeAsc(articul);
    }

    public List<Product> getAllProducts(){
        return repository.findAll();
    }

    public List<Product> getAllUniqueProducts(PageRequest pageRequest){
        var articulList = repository.selectDistinctArticul(pageRequest);
        List<Product> products = new ArrayList<>();
        for (Long articul : articulList) {
            products.add(repository.getDistinctFirstByArticulOrderByUpdatedTimeAsc(articul));
        }
        return products;
    }

    public List<Product> getAllProductsByArticul(Long articul){
        return repository.findProductsByArticulOrderByUpdatedTimeAsc(articul);
    }


    @Transactional
    public void saveAll(List<Product> products){
        products.forEach(product -> product.setUpdatedTime(LocalDate.now()));
        repository.saveAll(products);
    }

    @Transactional
    public void save(Product product1) {
        product1.setUpdatedTime(LocalDate.now());
        repository.save(product1);
    }

    @Transactional
    public void saveNotUpdatedItems(){
        // - [a] Выбрать все продукты (select distinct articul from products;)
        Set<Product> allProducts = new HashSet<>(repository.findAll());
        // - [b] Выбрать только те данные которые обновлены за сегодня(select distinct articul from products where updated_time='2023-05-15';)
        Set<Product> todayProducts = new HashSet<>(repository.findAllByUpdatedTime(LocalDate.now()));
        // - [ ] Исключить сегодняшние данные из выборки по артикулу и оставить только не обновленные  (b-a)
        allProducts.removeAll(todayProducts);
        // - [ ] Выбрать только уникальные артикулы не обновленные за сегодня
        List<Product> products = new ArrayList<>();
        for (Product e : allProducts) {
            products.add(
                    new Product(e.getArticul(), e.getName(), e.getPrice(), LocalDate.now())
            );
        }
        repository.saveAll(products);
    }
}
