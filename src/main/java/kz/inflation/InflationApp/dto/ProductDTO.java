package kz.inflation.InflationApp.dto;


import java.time.LocalDate;

public class ProductDTO {

    private Long articul;
    private String name;
    private int price;
    private LocalDate updatedTime;
    private ProductCategoryDTO category;

    public ProductDTO() {
    }

    public ProductDTO(Long articul, String name, int price,LocalDate updatedTime, ProductCategoryDTO category) {
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

    public ProductCategoryDTO getCategory() {
        return category;
    }

    public void setCategory(ProductCategoryDTO category) {
        this.category = category;
    }

    public LocalDate getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDate updatedTime) {
        this.updatedTime = updatedTime;
    }
}
