package kz.inflation.InflationApp.repositories.pharmacyRepository;

import kz.inflation.InflationApp.models.pharmacy.PharmacyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyCategoryRepository extends JpaRepository<PharmacyCategory, Long> {
    PharmacyCategory findFirstByNameLike(String name);
}
