package kz.inflation.InflationApp.models.products;

import jakarta.persistence.*;
import kz.inflation.InflationApp.models.abstractClasses.ItemInflationAbstract;
import org.checkerframework.checker.units.qual.C;

import java.time.LocalDate;

@Entity(name = "product_inflation")
public class ProductInflation extends ItemInflationAbstract {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductCount() {
        return productCount;
    }

    public void setProductCount(Long productCount) {
        this.productCount = productCount;
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
        return "ProductInflation{" +
                "id=" + id +
                ", productCount=" + productCount +
                ", totalPrice=" + totalPrice +
                ", averagePrice=" + averagePrice +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
