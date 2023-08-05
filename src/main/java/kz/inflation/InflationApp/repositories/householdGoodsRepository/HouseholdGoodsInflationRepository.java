package kz.inflation.InflationApp.repositories.householdGoodsRepository;

import kz.inflation.InflationApp.models.householdGoods.HouseholdGoodsInflation;
import kz.inflation.InflationApp.models.pharmacy.PharmacyInflation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseholdGoodsInflationRepository extends JpaRepository<HouseholdGoodsInflation, Long> {
}
