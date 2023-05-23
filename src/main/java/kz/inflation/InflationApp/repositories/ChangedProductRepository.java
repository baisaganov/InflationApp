package kz.inflation.InflationApp.repositories;

import kz.inflation.InflationApp.models.ChangedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ChangedProductRepository extends JpaRepository<ChangedProduct, Integer> {

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE changed_products", nativeQuery = true)
    void resetTable();

}
