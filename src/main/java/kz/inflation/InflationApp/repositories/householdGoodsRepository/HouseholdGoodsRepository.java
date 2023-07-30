package kz.inflation.InflationApp.repositories.householdGoodsRepository;

import kz.inflation.InflationApp.models.householdGoods.HouseholdGoods;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HouseholdGoodsRepository extends JpaRepository<HouseholdGoods, Long> {
    List<HouseholdGoods> findHouseholdGoodsByArticulOrderByUpdatedTimeAsc(Long articul);

    HouseholdGoods getDistinctFirstByArticulOrderByUpdatedTimeDesc(Long articul);

    @Query(value = "select p from household_goods p order by p.updatedTime desc")
    List<HouseholdGoods> getAllOrdered(PageRequest pageRequest);

    @Query(value = "select p from household_goods p where p.category.id=?1 order by p.updatedTime desc")
    List<HouseholdGoods> getAllOrderedWithCategory(int category, PageRequest pageRequest);

    /* *
        Метод возвращает все уникальные артикулы
    */
    @Query(value = "SELECT distinct articul from household_goods")
    List<Long> selectAllDistinctArticul();

    List<HouseholdGoods> getHouseholdGoodsByUpdatedTime(LocalDate date);

    List<HouseholdGoods> findDistinctTop2ByArticulOrderByUpdatedTimeDesc(Long articul);

    @Query(value = "select distinct (articul) from household_goods where upper(name) like upper(?1) escape '\\'")
    List<Long> searchHouseholdGoodsByName(String q, PageRequest pageRequest);
    //            "offset ?1 rows fetch first ?1 rows only ", nativeQuery = true)


    @Query(value = "select count(distinct (articul)) from household_goods where upper(name) like upper(?1) escape '\\'", nativeQuery = true)
    int searchHouseholdGoodsCount(String q);
}
