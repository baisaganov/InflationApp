package kz.inflation.InflationApp.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "changed_products")
public class ChangedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    int id;

    @Column(name = "articul")
    int articul;

    @Column(name = "name")
    String name;

    @Column(name = "price")
    int price ;

    @Column(name = "updated_time")
    LocalDate updatedTime;

    @Column(name = "category_id")
    int category_id;

    @Column(name = "category_name")
    String categoryName;

    @Column(name = "change_value")
    int changeValue;

    @Column(name = "change_percent")
    double changePercent;

    public ChangedProduct() {
    }

    public ChangedProduct(int articul, String name, int price, LocalDate updatedTime, int category_id, String categoryName, int changeValue, double changePercent) {
        this.articul = articul;
        this.name = name;
        this.price = price;
        this.updatedTime = updatedTime;
        this.category_id = category_id;
        this.categoryName = categoryName;
        this.changeValue = changeValue;
        this.changePercent = changePercent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticul() {
        return articul;
    }

    public void setArticul(int articul) {
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

    public LocalDate getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDate updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
