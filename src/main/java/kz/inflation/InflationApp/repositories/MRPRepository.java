package kz.inflation.InflationApp.repositories;

import kz.inflation.InflationApp.models.MRP;
import kz.inflation.InflationApp.models.RefinancingBids;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MRPRepository extends JpaRepository<MRP, Long> {
    MRP findDistinctFirstByYearOrderByYearDesc(Integer date);

    @Query(value = "select distinct year from mrp order by year desc ")
    List<Integer> getDistinctYear();

}
