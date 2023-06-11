package kz.inflation.InflationApp.models;

import jakarta.persistence.*;
import org.checkerframework.checker.units.qual.C;

import java.time.LocalDate;

@Entity(name = "product_inflation")
public class ProductInflation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_count")
    private Long productCount;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "average_price")
    private double averagePrice;

    @Column(name = "updated_time")
    private LocalDate updatedTime;

    public ProductInflation() {
    }

    public ProductInflation(Long productCount, Long totalPrice, double averagePrice, LocalDate updatedTime) {
        this.productCount = productCount;
        this.totalPrice = totalPrice;
        this.averagePrice = averagePrice;
        this.updatedTime = updatedTime;
    }

    public ProductInflation(Long productCount, Long totalPrice, double averagePrice) {
        this.productCount = productCount;
        this.totalPrice = totalPrice;
        this.averagePrice = averagePrice;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getProductCount() {
        return productCount;
    }

    public void setProductCount(Long productCount) {
        this.productCount = productCount;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public LocalDate getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDate updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "ProductInflation{" +
                "id=" + id +
                ", productCount=" + productCount +
                ", totalPrice=" + totalPrice +
                ", averagePrice=" + averagePrice +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
