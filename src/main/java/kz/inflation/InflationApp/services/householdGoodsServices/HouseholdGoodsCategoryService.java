package kz.inflation.InflationApp.services.householdGoodsServices;


import kz.inflation.InflationApp.models.householdGoods.HouseholdGoodsCategory;
import kz.inflation.InflationApp.models.pharmacy.PharmacyCategory;
import kz.inflation.InflationApp.repositories.householdGoodsRepository.HouseholdGoodsCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class HouseholdGoodsCategoryService {

    private final HouseholdGoodsCategoryRepository categoryRepository;

    @Autowired
    public HouseholdGoodsCategoryService(HouseholdGoodsCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public HouseholdGoodsCategory getCategory(String category) {
        category = "%" + category + "%";
        return categoryRepository.findFirstByNameLike(category);
    }

    public List<HouseholdGoodsCategory> findAll(){
        return categoryRepository.findAll();
    }
}
