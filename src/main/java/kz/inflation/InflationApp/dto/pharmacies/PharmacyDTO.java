package kz.inflation.InflationApp.dto.pharmacies;


import kz.inflation.InflationApp.dto.products.ProductCategoryDTO;

import java.time.LocalDate;

public class PharmacyDTO {

    private Long articul;
    private String name;
    private int price;
    private LocalDate updatedTime;
    private PharmacyCategoryDTO category;
    private int changeValue = 0;
    private double changePercent = 0.0;

    public PharmacyDTO() {
    }

    public PharmacyDTO(Long articul, String name, int price, LocalDate updatedTime, PharmacyCategoryDTO category) {
        this.articul = articul;
        this.name = name;
        this.price = price;
        this.updatedTime = updatedTime;
        this.category = category;
    }

    public Long getArticul() {
        return articul;
    }

    public void setArticul(Long articul) {
        this.articul = articul;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public PharmacyCategoryDTO getCategory() {
        return category;
    }

    public void setCategory(PharmacyCategoryDTO category) {
        this.category = category;
    }

    public LocalDate getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDate updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getChangeValue() {
        return changeValue;
    }

    public void setChangeValue(int changeValue) {
        this.changeValue = changeValue;
    }

    public double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(double changePercent) {
        this.changePercent = changePercent;
    }
}
