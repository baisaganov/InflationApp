package kz.inflation.InflationApp.repositories;

import kz.inflation.InflationApp.models.ProductInflation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInflationRepository extends JpaRepository<ProductInflation, Long> {
}
