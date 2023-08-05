package kz.inflation.InflationApp.repositories.productsRepository;

import kz.inflation.InflationApp.models.products.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    ProductCategory findFirstByNameLike(String name);
}
