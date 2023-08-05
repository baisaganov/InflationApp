package kz.inflation.InflationApp.services.pharmacyServices;

import kz.inflation.InflationApp.models.pharmacy.Pharmacy;
import kz.inflation.InflationApp.models.pharmacy.PharmacyInflation;
import kz.inflation.InflationApp.models.products.Product;
import kz.inflation.InflationApp.models.products.ProductInflation;
import kz.inflation.InflationApp.repositories.pharmacyRepository.PharmacyInflationRepository;
import kz.inflation.InflationApp.repositories.pharmacyRepository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PharmacyInflationService {
    private final PharmacyRepository pharmacyRepository;
    private final PharmacyInflationRepository pharmacyInflationRepository;

    @Autowired
    public PharmacyInflationService(PharmacyRepository pharmacyRepository, PharmacyInflationRepository pharmacyInflationRepository) {
        this.pharmacyRepository = pharmacyRepository;
        this.pharmacyInflationRepository = pharmacyInflationRepository;
    }

    public List<PharmacyInflation> getAllPharmacies(){
        return pharmacyInflationRepository.findAll();
    }

    @Transactional
    public void updateData(){
        LocalDate currentDate = LocalDate.now();
        List<Pharmacy> pharmacies = pharmacyRepository.getPharmaciesByUpdatedTime(currentDate);
        PharmacyInflation inflation = this.updateInflationInformation(pharmacies, currentDate);
        pharmacyInflationRepository.save(inflation);
    }

    public int todayPharmacyCount(){
        return pharmacyRepository.getPharmaciesByUpdatedTime(LocalDate.now()).size();
    }


    private PharmacyInflation updateInflationInformation(List<Pharmacy> pharmacies, LocalDate date){
        long count = (long) pharmacies.size();
        long sum = 0L;
        for (Pharmacy pharmacy : pharmacies) {
            sum += (long) pharmacy.getPrice();
        }
        return new PharmacyInflation(count,
                sum,
                sum/count,
                date);
    }


}
