package kz.inflation.InflationApp.services.pharmacyServices;

import kz.inflation.InflationApp.models.pharmacy.PharmacyCategory;
import kz.inflation.InflationApp.repositories.pharmacyRepository.PharmacyCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PharmacyCategoryService {

    private final PharmacyCategoryRepository pharmacyCategoryRepository;

    @Autowired
    public PharmacyCategoryService(PharmacyCategoryRepository pharmacyCategoryRepository) {
        this.pharmacyCategoryRepository = pharmacyCategoryRepository;
    }


    public PharmacyCategory getCategory(String category) {
        category = "%" + category + "%";
        return pharmacyCategoryRepository.findFirstByNameLike(category);
    }

    public List<PharmacyCategory> findAll(){
        return pharmacyCategoryRepository.findAll();
    }
}
