package kz.inflation.InflationApp.models.abstractClasses;

import java.time.LocalDate;

public abstract class ItemInflationAbstract {
    private Long id;
    private Long itemCount;
    private Long totalPrice;
    private double averagePrice;
    private LocalDate updatedTime;

    protected ItemInflationAbstract(){}

    protected ItemInflationAbstract(Long itemCount, Long totalPrice, double averagePrice, LocalDate updatedTime) {
        this.itemCount = itemCount;
        this.totalPrice = totalPrice;
        this.averagePrice = averagePrice;
        this.updatedTime = updatedTime;
    }

    public ItemInflationAbstract(Long itemCount, Long totalPrice, double averagePrice) {
        this.itemCount = itemCount;
        this.totalPrice = totalPrice;
        this.averagePrice = averagePrice;
    }

    public Long getItemCount() {
        return itemCount;
    }

    public void setItemCount(Long itemCount) {
        this.itemCount = itemCount;
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
        return "ItemInflationAbstract{" +
                "id=" + id +
                ", itemCount=" + itemCount +
                ", totalPrice=" + totalPrice +
                ", averagePrice=" + averagePrice +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
