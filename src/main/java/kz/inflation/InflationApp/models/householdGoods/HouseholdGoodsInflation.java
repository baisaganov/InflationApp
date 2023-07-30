package kz.inflation.InflationApp.models.householdGoods;

import jakarta.persistence.*;
import kz.inflation.InflationApp.models.abstractClasses.ItemInflationAbstract;

import java.time.LocalDate;

@Entity(name = "household_goods_inflation")
public class HouseholdGoodsInflation extends ItemInflationAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "household_goods_count")
    private Long householdGoodsCount;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "average_price")
    private double averagePrice;

    @Column(name = "updated_time")
    private LocalDate updatedTime;

    public HouseholdGoodsInflation() {
    }

    public HouseholdGoodsInflation(Long householdGoodsCount, Long totalPrice, double averagePrice, LocalDate updatedTime) {
        this.householdGoodsCount = householdGoodsCount;
        this.totalPrice = totalPrice;
        this.averagePrice = averagePrice;
        this.updatedTime = updatedTime;
    }

    public HouseholdGoodsInflation(Long householdGoodsCount, Long totalPrice, double averagePrice) {
        this.householdGoodsCount = householdGoodsCount;
        this.totalPrice = totalPrice;
        this.averagePrice = averagePrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHouseholdGoodsCount() {
        return householdGoodsCount;
    }

    public void setHouseholdGoodsCount(Long householdGoodsCount) {
        this.householdGoodsCount = householdGoodsCount;
    }

    @Override
    public Long getTotalPrice() {
        return totalPrice;
    }

    @Override
    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public double getAveragePrice() {
        return averagePrice;
    }

    @Override
    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
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
    public String toString() {
        return "HouseholdGoodsInflation{" +
                "id=" + id +
                ", householdGoodsCount=" + householdGoodsCount +
                ", totalPrice=" + totalPrice +
                ", averagePrice=" + averagePrice +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
