package kz.inflation.InflationApp.services;

import kz.inflation.InflationApp.models.ChangedProduct;
import kz.inflation.InflationApp.repositories.ChangedProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ChangedProductService {
    private final ChangedProductRepository repository;

    @Autowired
    public ChangedProductService(ChangedProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void saveAllChangedProduct(List<ChangedProduct> changedProductList){
        repository.saveAll(changedProductList);
    }

    public List<ChangedProduct> getAll() {
        return repository.findAll();
    }

    @Transactional
    public void resetTable() {
        repository.resetTable();
    }


}
