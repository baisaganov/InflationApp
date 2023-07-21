package kz.inflation.InflationApp.repositories.pharmacyRepository;

import kz.inflation.InflationApp.models.pharmacy.Pharmacy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    List<Pharmacy> findPharmaciesByArticulOrderByUpdatedTimeAsc(Long articul);

    Pharmacy getDistinctFirstByArticulOrderByUpdatedTimeDesc(Long articul);

    @Query(value = "select p from pharmacy p order by p.updatedTime desc")
    List<Pharmacy> getAllOrdered(PageRequest pageRequest);

    @Query(value = "select p from pharmacy p where p.category.id=?1 order by p.updatedTime desc")
    List<Pharmacy> getAllOrderedWithCategory(int category, PageRequest pageRequest);

    /* *
        Метод возвращает все уникальные артикулы
    */
    @Query(value = "SELECT distinct articul from pharmacy")
    List<Long> selectAllDistinctArticul();

    List<Pharmacy> getPharmaciesByUpdatedTime(LocalDate date);

    List<Pharmacy> findDistinctTop2ByArticulOrderByUpdatedTimeDesc(Long articul);

    @Query(value = "select distinct (articul) from pharmacy where upper(name) like upper(?1) escape '\\'")
    List<Long> searchPharmaciesByName(String q, PageRequest pageRequest);
    //            "offset ?1 rows fetch first ?1 rows only ", nativeQuery = true)


    @Query(value = "select count(distinct (articul)) from pharmacy where upper(name) like upper(?1) escape '\\'", nativeQuery = true)
    int searchPharmaciesCount(String q);
}
