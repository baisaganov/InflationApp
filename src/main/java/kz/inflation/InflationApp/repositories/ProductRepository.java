package kz.inflation.InflationApp.repositories;

import kz.inflation.InflationApp.models.Product;
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

    @Query(value = "SELECT distinct articul from products")
    List<Long> selectDistinctArticul(PageRequest pageRequest);

    @Query(value = "SELECT distinct articul from products where category.id=?1")
    List<Long> selectDistinctArticul(int category, PageRequest pageRequest);


    /* *
        Метод возвращает все уникальные артикулы
    */
    @Query(value = "SELECT distinct articul from products")
    List<Long> selectAllDistinctArticul();

    /* *
        Метод возвращает все уникальные артикулы обновленные за указанную дату
    */
    @Query(value = "SELECT distinct articul from products where updatedTime=?1")
    List<Long> selectAllDistinctArticulByDate(LocalDate date);


    Long countAllByUpdatedTime(LocalDate date);


    @Query(value = "select sum(price) from products where updatedTime=?1")
    Long sumAllByUpdatedTime(LocalDate date);

    List<Product> findDistinctTop2ByArticulOrderByUpdatedTimeDesc(Long articul);

    List<Product> findAllByUpdatedTime(LocalDate localDate);

    /*
    * Принимает на вход артикул, возвращает последний обновленный Продукт
    * */
    Product findTop1ByArticulOrderByUpdatedTimeDesc(Long articul);



    @Query(value = "select distinct (articul) from products where upper(name) like upper(?1) escape '\\'")
    List<Long> searchProductsByName(String q, PageRequest pageRequest);
    //            "offset ?1 rows fetch first ?1 rows only ", nativeQuery = true)


    @Query(value = "select count(distinct (articul)) from products where upper(name) like upper(?1) escape '\\'", nativeQuery = true)
    int searchProductsCount(String q);
}
