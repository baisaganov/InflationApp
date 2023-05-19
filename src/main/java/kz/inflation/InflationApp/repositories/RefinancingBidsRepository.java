package kz.inflation.InflationApp.repositories;

import kz.inflation.InflationApp.models.RefinancingBids;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Ref;
import java.time.LocalDate;
import java.util.List;

public interface RefinancingBidsRepository extends JpaRepository<RefinancingBids, Long> {

    RefinancingBids findDistinctFirstByYearOrderByYearAsc(LocalDate date);

    @Query(value = "select distinct year from refinancing_bids order by year desc ")
    List<LocalDate> getDistinctYear();
}
