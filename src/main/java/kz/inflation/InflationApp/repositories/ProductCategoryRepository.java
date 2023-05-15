package kz.inflation.InflationApp.repositories;

import kz.inflation.InflationApp.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    ProductCategory findFirstByName(String name);
}
