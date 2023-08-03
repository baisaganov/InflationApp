package kz.inflation.InflationApp.services.householdGoodsServices;


import kz.inflation.InflationApp.models.householdGoods.HouseholdGoods;
import kz.inflation.InflationApp.models.householdGoods.HouseholdGoodsInflation;
import kz.inflation.InflationApp.repositories.householdGoodsRepository.HouseholdGoodsInflationRepository;
import kz.inflation.InflationApp.repositories.householdGoodsRepository.HouseholdGoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class HouseholdGoodsInflationService {
    private final HouseholdGoodsRepository repository;
    private final HouseholdGoodsInflationRepository inflationRepository;

    @Autowired
    public HouseholdGoodsInflationService(HouseholdGoodsRepository repository, HouseholdGoodsInflationRepository inflationRepository) {
        this.repository = repository;
        this.inflationRepository =inflationRepository;
    }

    public List<HouseholdGoodsInflation> getAllHouseholdGoods(){
        return inflationRepository.findAll();
    }

    @Transactional
    public void updateData(){
        LocalDate currentDate = LocalDate.now();
        List<HouseholdGoods> pharmacies = repository.getHouseholdGoodsByUpdatedTime(currentDate);
        HouseholdGoodsInflation inflation = this.updateInflationInformation(pharmacies, currentDate);
        inflationRepository.save(inflation);
    }

    public int todayHouseholdGoodsCount(){
        return repository.getHouseholdGoodsByUpdatedTime(LocalDate.now()).size();
    }


    private HouseholdGoodsInflation updateInflationInformation(List<HouseholdGoods> goods, LocalDate date){
        long count = goods.size();
        count = count == 0 ? 1 : goods.size();
        long sum = 0L;
        for (HouseholdGoods householdGoods : goods) {
            sum += householdGoods.getPrice();
        }
        return new HouseholdGoodsInflation(count,
                sum,
                sum/count,
                date);
    }


}
