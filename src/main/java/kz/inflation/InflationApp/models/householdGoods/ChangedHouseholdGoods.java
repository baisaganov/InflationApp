package kz.inflation.InflationApp.models.householdGoods;

import jakarta.persistence.*;
import kz.inflation.InflationApp.models.abstractClasses.ChangedItemAbstract;

import java.time.LocalDate;

@Entity(name = "changed_household_goods")
public class ChangedHouseholdGoods extends ChangedItemAbstract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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

    public ChangedHouseholdGoods() {
    }

    public ChangedHouseholdGoods(int articul, String name, int price, LocalDate updatedTime, int category_id,
                                 String categoryName, int changeValue, double changePercent) {
        super(articul, name, price, updatedTime, category_id, categoryName, changeValue, changePercent);
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
