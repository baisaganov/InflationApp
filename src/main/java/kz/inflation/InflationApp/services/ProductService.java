package kz.inflation.InflationApp.services;

import kz.inflation.InflationApp.models.Product;
import kz.inflation.InflationApp.models.ProductCategory;
import kz.inflation.InflationApp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
        List<Product> list = repository.findAll();
        System.out.println(list.toString());
        return list;
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

    /*
        Функция обновляет не спарсенные продукты под текущую дату
    * */
    @Transactional
    public List<Product> saveNotUpdatedItems(){
        //Взять все продукты
        List<Product> list = repository.findAll();
        //Отсортировать по дате
        list.sort(Comparator.comparing(Product::getUpdatedTime).reversed());

        //Поместить в сет
        List<Product> unique = new HashSet<>(list).stream().toList();
        System.out.println("Total unique: " + unique.size());
        this.findClones(unique);
        //Убрать и сета все обновленные продукты
        List<Product> notUpdated = unique.stream().filter(e -> !(e.getUpdatedTime().equals(LocalDate.now()))).toList();

        //Оставшиеся продукты конвертировать в сегодняшний день
        List<Product> toSave = new ArrayList<>();
        for (Product product : notUpdated) {
            Product newProduct = new Product(
                    product.getArticul(),
                    product.getName(),
                    product.getPrice(),
                    LocalDate.now()
            );
            newProduct.setCategory(product.getCategory());
            toSave.add(newProduct);
        }
        repository.saveAll(toSave);
        return toSave;
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

    private void findClones(List<Product> productList){
        List<Long> articuls = productList.stream().map(Product::getArticul).toList();
        HashSet<Long> longHashSet = new HashSet<>(articuls);
        System.out.println("Articuls size: " + articuls.size());
        System.out.println("longHashSet size: " + longHashSet.size());
    }
}
