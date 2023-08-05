package kz.inflation.InflationApp.services.pharmacyServices;

import kz.inflation.InflationApp.models.pharmacy.Pharmacy;
import kz.inflation.InflationApp.models.products.Product;
import kz.inflation.InflationApp.repositories.pharmacyRepository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PharmacyService {
    private final PharmacyRepository repository;

    @Autowired
    public PharmacyService(PharmacyRepository repository) {
        this.repository = repository;
    }

    public List<Pharmacy> findAllByArticul(Long articul){
        return repository.findPharmaciesByArticulOrderByUpdatedTimeAsc(articul);
    }

    public List<Pharmacy> getAllPharmacies(){
        return repository.findAll();
    }

    public List<Long> getAllUniqueArticuls(){
        return repository.selectAllDistinctArticul();
    }

    public List<Pharmacy> getAllUniquePharmacies(PageRequest pageRequest, int category){
        return category == 0 ? repository.getAllOrdered(pageRequest) : repository.getAllOrderedWithCategory(category, pageRequest);
    }

    public List<Pharmacy> getAllPharmaciesByArticul(Long articul){
        return repository.findPharmaciesByArticulOrderByUpdatedTimeAsc(articul);
    }

    public List<Pharmacy> findTop2(Long articul){
        List<Pharmacy> pharmacyList = repository.findDistinctTop2ByArticulOrderByUpdatedTimeDesc(articul);
        if(pharmacyList.size() > 1 && pharmacyList.get(0).getPrice() !=  pharmacyList.get(1).getPrice())
            return pharmacyList;
        return null;
    }

    public List<List<Pharmacy>> lastPriceChangeItems(){
        List<List<Pharmacy>> list = new ArrayList<>();
        List<Long> articuls =  this.getAllUniqueArticuls();
        for (Long articul : articuls) {
            List<Pharmacy> pharmacyList = this.findTop2(articul);
            if (pharmacyList != null)
                list.add(pharmacyList);
        }
        return list;
    }


    @Transactional
    public void saveAll(List<Pharmacy> pharmacies){
        pharmacies.forEach(product -> product.setUpdatedTime(LocalDate.now()));
        repository.saveAll(pharmacies);
    }

    @Transactional
    public void save(Pharmacy pharmacy) {
        pharmacy.setUpdatedTime(LocalDate.now());
        repository.save(pharmacy);
    }

    /*
        Функция обновляет не спарсенные продукты под текущую дату
    * */
    @Transactional
    public List<Pharmacy> saveNotUpdatedItems(){
        //Взять все продукты
        List<Pharmacy> list = repository.findAll();
        //Отсортировать по дате
        list.sort(Comparator.comparing(Pharmacy::getUpdatedTime).reversed());

        //Поместить в сет
        List<Pharmacy> unique = new HashSet<>(list).stream().toList();
        this.findClones(unique);
        //Убрать и сета все обновленные продукты
        List<Pharmacy> notUpdated = unique.stream().filter(e -> !(e.getUpdatedTime().equals(LocalDate.now()))).toList();

        //Оставшиеся продукты конвертировать в сегодняшний день
        List<Pharmacy> toSave = new ArrayList<>();
        for (Pharmacy pharmacy : notUpdated) {
            Pharmacy newPharmacy = new Pharmacy(
                    pharmacy.getArticul(),
                    pharmacy.getName(),
                    pharmacy.getPrice(),
                    LocalDate.now()
            );
            newPharmacy.setCategory(pharmacy.getCategory());
            toSave.add(newPharmacy);
        }
        repository.saveAll(toSave);
        return toSave;
    }

    public Pharmacy getPharmacyByArticul(Long articul){
        return repository.getDistinctFirstByArticulOrderByUpdatedTimeDesc(articul);
    }


    public List<Pharmacy> findByName(String q, PageRequest pageRequest) {
        q = '%' + q + '%';
        List<Long> articuls = repository.searchPharmaciesByName(q, pageRequest);
        List<Pharmacy> pharmacies = new ArrayList<>();
        for (Long articul : articuls) {
            pharmacies.add(repository.getDistinctFirstByArticulOrderByUpdatedTimeDesc(articul));
        }
        return pharmacies;
    }

    public int searchCount(String q){
        q = '%' + q + '%';
        return repository.searchPharmaciesCount(q);
    }

    private void findClones(List<Pharmacy> pharmacyList){
        List<Long> articuls = pharmacyList.stream().map(Pharmacy::getArticul).toList();
        HashSet<Long> longHashSet = new HashSet<>(articuls);
        System.out.println("Articuls size: " + articuls.size());
        System.out.println("longHashSet size: " + longHashSet.size());
    }
}
