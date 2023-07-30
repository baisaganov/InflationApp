package kz.inflation.InflationApp.services.householdGoodsServices;

import kz.inflation.InflationApp.models.householdGoods.ChangedHouseholdGoods;
import kz.inflation.InflationApp.repositories.householdGoodsRepository.ChangedHouseholdGoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ChangedHouseholdGoodsService {
    private final ChangedHouseholdGoodsRepository repository;

    @Autowired
    public ChangedHouseholdGoodsService(ChangedHouseholdGoodsRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void saveAllChangedHouseholdGoods(List<ChangedHouseholdGoods> changedList){
        repository.saveAll(changedList);
    }

    public List<ChangedHouseholdGoods> getAll() {
        return repository.findAll();
    }

    @Transactional
    public void resetTable() {
        repository.resetTable();
    }


}
