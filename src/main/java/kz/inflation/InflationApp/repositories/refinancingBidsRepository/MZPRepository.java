package kz.inflation.InflationApp.repositories.refinancingBidsRepository;

import kz.inflation.InflationApp.models.refinancingBids.MZP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MZPRepository extends JpaRepository<MZP, Long> {
    MZP findDistinctFirstByYearOrderByYearDesc(Integer date);

    @Query(value = "select distinct year from mzp order by year desc ")
    List<Integer> getDistinctYear();
}
