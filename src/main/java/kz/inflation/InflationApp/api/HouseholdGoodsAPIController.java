package kz.inflation.InflationApp.api;



import kz.inflation.InflationApp.dto.householdGoods.HouseholdGoodsCategoryDTO;
import kz.inflation.InflationApp.dto.householdGoods.HouseholdGoodsDTO;
import kz.inflation.InflationApp.models.householdGoods.ChangedHouseholdGoods;
import kz.inflation.InflationApp.models.householdGoods.HouseholdGoods;
import kz.inflation.InflationApp.models.householdGoods.HouseholdGoodsCategory;
import kz.inflation.InflationApp.models.householdGoods.HouseholdGoodsInflation;
import kz.inflation.InflationApp.scripts.householdGoods.HouseholdGoodsParser;
import kz.inflation.InflationApp.services.householdGoodsServices.ChangedHouseholdGoodsService;
import kz.inflation.InflationApp.services.householdGoodsServices.HouseholdGoodsCategoryService;
import kz.inflation.InflationApp.services.householdGoodsServices.HouseholdGoodsInflationService;
import kz.inflation.InflationApp.services.householdGoodsServices.HouseholdGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/household-goods")
public class HouseholdGoodsAPIController {

    private final HouseholdGoodsService itemService;
    private final HouseholdGoodsParser parser;
    private final HouseholdGoodsInflationService itemInflationService;
    private final ChangedHouseholdGoodsService changedItemService;
    private final HouseholdGoodsCategoryService categoryService;

    @Autowired
    public HouseholdGoodsAPIController(HouseholdGoodsService itemService, HouseholdGoodsParser parser, HouseholdGoodsInflationService itemInflationService, ChangedHouseholdGoodsService changedItemService, HouseholdGoodsCategoryService categoryService) {
        this.itemService = itemService;
        this.parser = parser;
        this.itemInflationService = itemInflationService;
        this.changedItemService = changedItemService;
        this.categoryService = categoryService;
    }

    @GetMapping()
    public List<HouseholdGoodsDTO> pharmacies() {
        return convertListToItemDTO(itemService.getAllHouseholdGoods());
    }

    @GetMapping("/not-updated")
    public List<HouseholdGoodsDTO> updated3(){
        return convertListToItemDTO(itemService.saveNotUpdatedItems());
    }



    @GetMapping("/{articul}")
    public List<HouseholdGoodsDTO> getPharmacyByArticul(@PathVariable Long articul) {
        return convertListToItemDTO(itemService.getAllHouseholdGoodsByArticul(articul));
    }

    @GetMapping("/unique")
    public List<HouseholdGoodsDTO> getUniqueProducts(@RequestParam(required = false, defaultValue = "0") int page,
                                              @RequestParam(required = false, defaultValue = "0", name="category") int category,
                                              @RequestParam(required = false, defaultValue = "12") int size
                                              ){
        return convertListToItemDTO(itemService.getAllUniqueHouseholdGoods(PageRequest.of(page, size), category));
    }

    @GetMapping("/inflation")
    public List<HouseholdGoodsInflation> productInflations(){
        return itemInflationService.getAllHouseholdGoods();
    }

    @GetMapping("/changed-list")
    public List<ChangedHouseholdGoods> lastChange(){
        return changedItemService.getAll();
    }

    @GetMapping("/categories")
    public List<HouseholdGoodsCategoryDTO> categoryDTOList(){
        return this.convertListToCategoryDTO(categoryService.findAll());
    }

    @GetMapping("/update-not-updated")
    public ResponseEntity<String> update(){
        itemService.saveNotUpdatedItems();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/today-count")
    public int todayProductCount(){
        return itemInflationService.todayHouseholdGoodsCount();
    }

    private List<HouseholdGoodsDTO> convertListToItemDTO(List<HouseholdGoods> items) {
        List<HouseholdGoodsDTO> dtoList = new ArrayList<>();
        for (HouseholdGoods item : items) {
            dtoList.add(new HouseholdGoodsDTO(
                    item.getArticul(),
                    item.getName(),
                    item.getPrice(),
                    item.getUpdatedTime(),
                    convertToCategoryDTO(item.getCategory())));
        }
        return dtoList;
    }

    private HouseholdGoodsCategoryDTO convertToCategoryDTO(HouseholdGoodsCategory category) {
        return new HouseholdGoodsCategoryDTO(category.getId(), category.getName());
    }

    private List<HouseholdGoodsCategoryDTO> convertListToCategoryDTO(List<HouseholdGoodsCategory> categoryList) {
        return categoryList.stream()
                .map(e -> new HouseholdGoodsCategoryDTO(e.getId(), e.getName()))
                .collect(Collectors.toList());
    }


}
