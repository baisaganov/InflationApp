package kz.inflation.InflationApp.repositories;

import kz.inflation.InflationApp.models.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
    Product findDistinctByArticulOrderByUpdatedTimeDesc(Long articul);


//    List<Product> findDistinctFirstByArticul(Set<Long> articul);
}
