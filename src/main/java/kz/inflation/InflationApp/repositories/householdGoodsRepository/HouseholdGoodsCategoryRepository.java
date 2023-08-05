package kz.inflation.InflationApp.repositories.householdGoodsRepository;

import kz.inflation.InflationApp.models.householdGoods.HouseholdGoodsCategory;
import kz.inflation.InflationApp.models.pharmacy.PharmacyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseholdGoodsCategoryRepository extends JpaRepository<HouseholdGoodsCategory, Long> {
    HouseholdGoodsCategory findFirstByNameLike(String name);
}
