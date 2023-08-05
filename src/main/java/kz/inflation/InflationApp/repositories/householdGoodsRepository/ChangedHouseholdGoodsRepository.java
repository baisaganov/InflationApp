package kz.inflation.InflationApp.repositories.householdGoodsRepository;

import kz.inflation.InflationApp.models.householdGoods.ChangedHouseholdGoods;
import kz.inflation.InflationApp.models.pharmacy.ChangedPharmacyAbstract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ChangedHouseholdGoodsRepository extends JpaRepository<ChangedHouseholdGoods, Integer> {

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE changed_household_goods", nativeQuery = true)
    void resetTable();

}
