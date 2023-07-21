package kz.inflation.InflationApp.models.pharmacy;

import jakarta.persistence.*;
import kz.inflation.InflationApp.models.abstractClasses.ItemInflationAbstract;

import java.time.LocalDate;

@Entity(name = "pharmacy_inflation")
public class PharmacyInflation extends ItemInflationAbstract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pharmacy_count")
    private Long pharmacyCount;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "average_price")
    private double averagePrice;

    @Column(name = "updated_time")
    private LocalDate updatedTime;

    public PharmacyInflation() {
    }

    public PharmacyInflation(Long pharmacyCount, Long totalPrice, double averagePrice, LocalDate updatedTime) {
        this.pharmacyCount = pharmacyCount;
        this.totalPrice = totalPrice;
        this.averagePrice = averagePrice;
        this.updatedTime = updatedTime;
    }

    public PharmacyInflation(Long pharmacyCount, Long totalPrice, double averagePrice) {
        this.pharmacyCount = pharmacyCount;
        this.totalPrice = totalPrice;
        this.averagePrice = averagePrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPharmacyCount() {
        return pharmacyCount;
    }

    public void setPharmacyCount(Long pharmacyCount) {
        this.pharmacyCount = pharmacyCount;
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
        return "PharmacyInflation{" +
                "id=" + id +
                ", pharmacyCount=" + pharmacyCount +
                ", totalPrice=" + totalPrice +
                ", averagePrice=" + averagePrice +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
