package kz.inflation.InflationApp.repositories.productsRepository;

import kz.inflation.InflationApp.models.products.Product;
import kz.inflation.InflationApp.models.products.ProductInflation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInflationRepository extends JpaRepository<ProductInflation, Long> {

    @Query(value = "select p from products p order by p.updatedTime desc")
    List<ProductInflation> getAllOrdered();
}
