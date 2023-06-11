package kz.inflation.InflationApp.services;

import kz.inflation.InflationApp.models.Product;
import kz.inflation.InflationApp.models.ProductCategory;
import kz.inflation.InflationApp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    public List<Long> getAllUniqueArticuls(){
        return repository.selectAllDistinctArticul();
    }

    public List<Product> getAllUniqueProducts(PageRequest pageRequest, int category){
        List<Long> articulList = category == 0 ? repository.selectDistinctArticul(pageRequest) : repository.selectDistinctArticul(category, pageRequest);

        List<Product> products = new ArrayList<>();
        for (Long articul : articulList) {
            products.add(repository.getDistinctFirstByArticulOrderByUpdatedTimeDesc(articul));
        }
        return products;
    }

    public List<Product> getAllProductsByArticul(Long articul){
        return repository.findProductsByArticulOrderByUpdatedTimeAsc(articul);
    }

    public List<Product> findTop2(Long articul){
        List<Product> productList = repository.findDistinctTop2ByArticulOrderByUpdatedTimeDesc(articul);
        if(productList.size() > 1 && productList.get(0).getPrice() !=  productList.get(1).getPrice())
            return productList;
        return null;
    }

    public List<List<Product>> lastPriceChangeItems(){
        List<List<Product>> list = new ArrayList<>();
        List<Long> articuls =  this.getAllUniqueArticuls();
        for (Long articul : articuls) {
            List<Product> productList = this.findTop2(articul);
            if (productList != null)
                list.add(productList);
        }
        return list;
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
            Product product = new Product(e.getArticul(), e.getName(), e.getPrice(), LocalDate.now());
            ProductCategory category = e.getCategory();
            product.setCategory(category);
            products.add(product);
        }
        repository.saveAll(products);
    }

    @Transactional
    public void saveNotUpdatedItems2(){
        // - [a] Выбрать все уникальные артикулы (select distinct articul from products;)
        List<Long> allArticuls = repository.selectAllDistinctArticul();
        // - [b] Выбрать только те артикулы которые обновлены за сегодня(select distinct articul from products where updated_time='2023-05-15';)
        List<Long> todayArticuls = repository.selectAllDistinctArticulByDate(LocalDate.now());
        // - [ ] Исключить сегодняшние данные из выборки по артикулу и оставить только не обновленные  (b-a)
        allArticuls.removeAll(todayArticuls);
        // - [ ] Получить продукты по оставшимся артикулам с сортировкой DESC и сохранить текущим числом
        List<Product> updatedProductList = new ArrayList<>();
        for (Long articul : allArticuls) {
            Product product = repository.findDistinctByArticulOrderByUpdatedTimeDesc(articul);
            Product newProduct = new Product(
                    product.getArticul(),
                    product.getName(),
                    product.getPrice(),
                    LocalDate.now()
            );
            updatedProductList.add(newProduct);
        }
        repository.saveAll(updatedProductList);
    }

    public Product getProductByArticul(Long articul){
        return repository.getDistinctFirstByArticulOrderByUpdatedTimeDesc(articul);
    }


    public List<Product> findByName(String q, PageRequest pageRequest) {
        q = '%' + q + '%';
        List<Long> articuls = repository.searchProductsByName(q, pageRequest);
        System.out.println(articuls.toString());
        List<Product> products = new ArrayList<>();
        for (Long articul : articuls) {
            products.add(repository.getDistinctFirstByArticulOrderByUpdatedTimeDesc(articul));
        }
        return products;
    }

    public int searchCount(String q){
        q = '%' + q + '%';
        return repository.searchProductsCount(q);
    }
}
