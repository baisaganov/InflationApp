package kz.inflation.InflationApp.repositories.pharmacyRepository;

import kz.inflation.InflationApp.models.pharmacy.ChangedPharmacyAbstract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ChangedPharmacyRepository extends JpaRepository<ChangedPharmacyAbstract, Integer> {

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE changed_pharmacy", nativeQuery = true)
    void resetTable();

}
