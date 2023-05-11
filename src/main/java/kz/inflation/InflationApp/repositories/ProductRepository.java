package kz.inflation.InflationApp.repositories;

import kz.inflation.InflationApp.models.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByArticulOrderByUpdatedTimeAsc(Long articul);


    Product getDistinctFirstByArticulOrderByUpdatedTimeAsc(Long articul);

    @Query(value = "SELECT distinct articul from products")
    List<Long> selectDistinctArticul(PageRequest pageRequest);
}
