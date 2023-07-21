package kz.inflation.InflationApp.repositories.pharmacyRepository;

import kz.inflation.InflationApp.models.pharmacy.PharmacyInflation;
import kz.inflation.InflationApp.models.products.ProductInflation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyInflationRepository extends JpaRepository<PharmacyInflation, Long> {
}
