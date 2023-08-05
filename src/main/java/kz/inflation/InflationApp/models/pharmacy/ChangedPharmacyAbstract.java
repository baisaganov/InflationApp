package kz.inflation.InflationApp.models.pharmacy;

import jakarta.persistence.*;
import kz.inflation.InflationApp.models.abstractClasses.ChangedItemAbstract;

import java.time.LocalDate;

@Entity(name = "changed_pharmacy")
public class ChangedPharmacyAbstract extends ChangedItemAbstract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private int id;

    @Column(name = "articul")
    private int articul;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price ;

    @Column(name = "updated_time")
    private LocalDate updatedTime;

    @Column(name = "category_id")
    private int category_id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "change_value")
    private int changeValue;

    @Column(name = "change_percent")
    private double changePercent;

    public ChangedPharmacyAbstract() {
    }

    public ChangedPharmacyAbstract(int articul, String name, int price, LocalDate updatedTime, int category_id, String categoryName, int changeValue, double changePercent) {
        this.articul = articul;
        this.name = name;
        this.price = price;
        this.updatedTime = updatedTime;
        this.category_id = category_id;
        this.categoryName = categoryName;
        this.changeValue = changeValue;
        this.changePercent = changePercent;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getArticul() {
        return articul;
    }

    @Override
    public void setArticul(int articul) {
        this.articul = articul;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public LocalDate getUpdatedTime() {
        return updatedTime;
    }

    @Override
    public void setUpdatedTime(LocalDate updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public int getCategory_id() {
        return category_id;
    }

    @Override
    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    @Override
    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public int getChangeValue() {
        return changeValue;
    }

    @Override
    public void setChangeValue(int changeValue) {
        this.changeValue = changeValue;
    }

    @Override
    public double getChangePercent() {
        return changePercent;
    }

    @Override
    public void setChangePercent(double changePercent) {
        this.changePercent = changePercent;
    }
}
