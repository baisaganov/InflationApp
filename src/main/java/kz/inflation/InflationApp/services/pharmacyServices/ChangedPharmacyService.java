package kz.inflation.InflationApp.services.pharmacyServices;

import kz.inflation.InflationApp.models.pharmacy.ChangedPharmacyAbstract;
import kz.inflation.InflationApp.repositories.pharmacyRepository.ChangedPharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ChangedPharmacyService {
    private final ChangedPharmacyRepository repository;

    @Autowired
    public ChangedPharmacyService(ChangedPharmacyRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void saveAllChangedPharmacy(List<ChangedPharmacyAbstract> changedPharmacyList){
        repository.saveAll(changedPharmacyList);
    }

    public List<ChangedPharmacyAbstract> getAll() {
        return repository.findAll();
    }

    @Transactional
    public void resetTable() {
        repository.resetTable();
    }


}
