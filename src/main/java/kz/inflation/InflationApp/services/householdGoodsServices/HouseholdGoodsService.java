package kz.inflation.InflationApp.services.householdGoodsServices;

import kz.inflation.InflationApp.models.householdGoods.HouseholdGoods;
import kz.inflation.InflationApp.repositories.householdGoodsRepository.HouseholdGoodsRepository;
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
public class HouseholdGoodsService {
    private final HouseholdGoodsRepository repository;

    @Autowired
    public HouseholdGoodsService(HouseholdGoodsRepository repository) {
        this.repository = repository;
    }

    public List<HouseholdGoods> findAllByArticul(Long articul){
        return repository.findHouseholdGoodsByArticulOrderByUpdatedTimeAsc(articul);
    }

    public List<HouseholdGoods> getAllHouseholdGoods(){
        return repository.findAll();
    }

    public List<Long> getAllUniqueArticuls(){
        return repository.selectAllDistinctArticul();
    }

    public List<HouseholdGoods> getAllUniqueHouseholdGoods(PageRequest pageRequest, int category){
        return category == 0 ? repository.getAllOrdered(pageRequest) : repository.getAllOrderedWithCategory(category, pageRequest);
    }

    public List<HouseholdGoods> getAllHouseholdGoodsByArticul(Long articul){
        return repository.findHouseholdGoodsByArticulOrderByUpdatedTimeAsc(articul);
    }

    public List<HouseholdGoods> findTop2(Long articul){
        List<HouseholdGoods> householdGoodsList = repository.findDistinctTop2ByArticulOrderByUpdatedTimeDesc(articul);
        if(householdGoodsList.size() > 1 && householdGoodsList.get(0).getPrice() !=  householdGoodsList.get(1).getPrice())
            return householdGoodsList;
        return null;
    }

    public List<List<HouseholdGoods>> lastPriceChangeItems(){
        List<List<HouseholdGoods>> list = new ArrayList<>();
        List<Long> articuls =  this.getAllUniqueArticuls();
        for (Long articul : articuls) {
            List<HouseholdGoods> householdGoodsList = this.findTop2(articul);
            if (householdGoodsList != null)
                list.add(householdGoodsList);
        }
        return list;
    }


    @Transactional
    public void saveAll(List<HouseholdGoods> pharmacies){
        pharmacies.forEach(product -> product.setUpdatedTime(LocalDate.now()));
        repository.saveAll(pharmacies);
    }

    @Transactional
    public void save(HouseholdGoods pharmacy) {
        pharmacy.setUpdatedTime(LocalDate.now());
        repository.save(pharmacy);
    }

    /*
        Функция обновляет не спарсенные продукты под текущую дату
    * */
    @Transactional
    public List<HouseholdGoods> saveNotUpdatedItems(){
        //Взять все продукты
        List<HouseholdGoods> list = repository.findAll();
        //Отсортировать по дате
        list.sort(Comparator.comparing(HouseholdGoods::getUpdatedTime).reversed());

        //Поместить в сет
        List<HouseholdGoods> unique = new HashSet<>(list).stream().toList();
        this.findClones(unique);
        //Убрать и сета все обновленные продукты
        List<HouseholdGoods> notUpdated = unique.stream().filter(e -> !(e.getUpdatedTime().equals(LocalDate.now()))).toList();

        //Оставшиеся продукты конвертировать в сегодняшний день
        List<HouseholdGoods> toSave = new ArrayList<>();
        for (HouseholdGoods householdGoods : notUpdated) {
            HouseholdGoods newHouseholdGoods = new HouseholdGoods(
                    householdGoods.getArticul(),
                    householdGoods.getName(),
                    householdGoods.getPrice(),
                    LocalDate.now()
            );
            newHouseholdGoods.setCategory(householdGoods.getCategory());
            toSave.add(newHouseholdGoods);
        }
        repository.saveAll(toSave);
        return toSave;
    }

    public HouseholdGoods getHouseholdGoodsByArticul(Long articul){
        return repository.getDistinctFirstByArticulOrderByUpdatedTimeDesc(articul);
    }


    public List<HouseholdGoods> findByName(String q, PageRequest pageRequest) {
        q = '%' + q + '%';
        List<Long> articuls = repository.searchHouseholdGoodsByName(q, pageRequest);
        List<HouseholdGoods> householdGoods = new ArrayList<>();
        for (Long articul : articuls) {
            householdGoods.add(repository.getDistinctFirstByArticulOrderByUpdatedTimeDesc(articul));
        }
        return householdGoods;
    }

    public int searchCount(String q){
        q = '%' + q + '%';
        return repository.searchHouseholdGoodsCount(q);
    }

    private void findClones(List<HouseholdGoods> householdGoodsList){
        List<Long> articuls = householdGoodsList.stream().map(HouseholdGoods::getArticul).toList();
        HashSet<Long> longHashSet = new HashSet<>(articuls);
        System.out.println("Articuls size: " + articuls.size());
        System.out.println("longHashSet size: " + longHashSet.size());
    }
}
