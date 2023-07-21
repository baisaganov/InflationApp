package kz.inflation.InflationApp.repositories.productsRepository;

import kz.inflation.InflationApp.models.products.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByArticulOrderByUpdatedTimeAsc(Long articul);

    Product getDistinctFirstByArticulOrderByUpdatedTimeDesc(Long articul);

    @Query(value = "select p from products p order by p.updatedTime desc")
    List<Product> getAllOrdered(PageRequest pageRequest);

    @Query(value = "select p from products p where p.category.id=?1 order by p.updatedTime desc")
    List<Product> getAllOrderedWithCategory(int category, PageRequest pageRequest);

    /* *
        Метод возвращает все уникальные артикулы
    */
    @Query(value = "SELECT distinct articul from products")
    List<Long> selectAllDistinctArticul();

    List<Product> getProductsByUpdatedTime(LocalDate date);

    List<Product> findDistinctTop2ByArticulOrderByUpdatedTimeDesc(Long articul);

    @Query(value = "select distinct (articul) from products where upper(name) like upper(?1) escape '\\'")
    List<Long> searchProductsByName(String q, PageRequest pageRequest);
    //            "offset ?1 rows fetch first ?1 rows only ", nativeQuery = true)


    @Query(value = "select count(distinct (articul)) from products where upper(name) like upper(?1) escape '\\'", nativeQuery = true)
    int searchProductsCount(String q);
}
