package kz.inflation.InflationApp.api;

import kz.inflation.InflationApp.dto.pharmacies.PharmacyCategoryDTO;
import kz.inflation.InflationApp.dto.pharmacies.PharmacyDTO;
import kz.inflation.InflationApp.models.pharmacy.ChangedPharmacyAbstract;
import kz.inflation.InflationApp.models.pharmacy.Pharmacy;
import kz.inflation.InflationApp.models.pharmacy.PharmacyCategory;
import kz.inflation.InflationApp.models.pharmacy.PharmacyInflation;
import kz.inflation.InflationApp.scripts.pharmaciesParse.PharmacyParser;
import kz.inflation.InflationApp.services.pharmacyServices.ChangedPharmacyService;
import kz.inflation.InflationApp.services.pharmacyServices.PharmacyCategoryService;
import kz.inflation.InflationApp.services.pharmacyServices.PharmacyInflationService;
import kz.inflation.InflationApp.services.pharmacyServices.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pharmacy")
public class PharmacyAPIController {

    private final PharmacyService pharmacyService;
    private final PharmacyParser pharmacyParser;
    private final PharmacyInflationService pharmacyInflationService;
    private final ChangedPharmacyService changedPharmacyService;
    private final PharmacyCategoryService categoryService;

    @Autowired
    public PharmacyAPIController(PharmacyService pharmacyService, PharmacyParser pharmacyParser, PharmacyInflationService pharmacyInflationService, ChangedPharmacyService changedPharmacyService, PharmacyCategoryService categoryService) {
        this.pharmacyService = pharmacyService;
        this.pharmacyParser = pharmacyParser;
        this.pharmacyInflationService = pharmacyInflationService;
        this.changedPharmacyService = changedPharmacyService;
        this.categoryService = categoryService;
    }

    @GetMapping()
    public List<PharmacyDTO> pharmacies() {
        return convertListToPharmacyDTO(pharmacyService.getAllPharmacies());
    }

    @GetMapping("/not-updated")
    public List<PharmacyDTO> updated3(){
        return convertListToPharmacyDTO(pharmacyService.saveNotUpdatedItems());
    }



    @GetMapping("/{articul}")
    public List<PharmacyDTO> getPharmacyByArticul(@PathVariable Long articul) {
        return convertListToPharmacyDTO(pharmacyService.getAllPharmaciesByArticul(articul));
    }

    @GetMapping("/unique")
    public List<PharmacyDTO> getUniqueProducts(@RequestParam(required = false, defaultValue = "0") int page,
                                              @RequestParam(required = false, defaultValue = "0", name="category") int category,
                                              @RequestParam(required = false, defaultValue = "12") int size
                                              ){
        return convertListToPharmacyDTO(pharmacyService.getAllUniquePharmacies(PageRequest.of(page, size), category));
    }

    @GetMapping("/inflation")
    public List<PharmacyInflation> productInflations(){
        return pharmacyInflationService.getAllPharmacies();
    }

    @GetMapping("/changed-list")
    public List<ChangedPharmacyAbstract> lastChange(){
        return changedPharmacyService.getAll();
    }

    @GetMapping("/categories")
    public List<PharmacyCategoryDTO> categoryDTOList(){
        return this.convertListToCategoryDTO(categoryService.findAll());
    }

    @GetMapping("/update-not-updated")
    public ResponseEntity<String> update(){
        pharmacyService.saveNotUpdatedItems();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/today-count")
    public int todayProductCount(){
        return pharmacyInflationService.todayPharmacyCount();
    }

    private List<PharmacyDTO> convertListToPharmacyDTO(List<Pharmacy> products) {
        List<PharmacyDTO> dtoList = new ArrayList<>();
        for (Pharmacy e : products) {
            dtoList.add(new PharmacyDTO(
                    e.getArticul(),
                    e.getName(),
                    e.getPrice(),
                    e.getUpdatedTime(),
                    convertToCategoryDTO((PharmacyCategory) e.getCategory())));
        }
        return dtoList;
    }

    private PharmacyCategoryDTO convertToCategoryDTO(PharmacyCategory category) {
        return new PharmacyCategoryDTO(category.getId(), category.getName());
    }

    private List<PharmacyCategoryDTO> convertListToCategoryDTO(List<PharmacyCategory> categoryList) {
        return categoryList.stream()
                .map(e -> new PharmacyCategoryDTO(e.getId(), e.getName()))
                .collect(Collectors.toList());
        //        for (ProductCategory e : categoryList) {
//            dtoList.add(new ProductCategoryDTO(
//                    e.getId(),
//                    e.getName()
//            ));
//        }
    }


}
