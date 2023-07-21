package kz.inflation.InflationApp.repositories.refinancingBidsRepository;

import kz.inflation.InflationApp.models.refinancingBids.MRP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MRPRepository extends JpaRepository<MRP, Long> {
    MRP findDistinctFirstByYearOrderByYearDesc(Integer date);

    @Query(value = "select distinct year from mrp order by year desc ")
    List<Integer> getDistinctYear();

}
