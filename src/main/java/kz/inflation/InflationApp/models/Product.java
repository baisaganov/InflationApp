package kz.inflation.InflationApp.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "articul")
    private Long articul;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "updated_time")
    private LocalDate updatedTime;

    public Product() {
    }

    public Product(Long articul, String name, int price) {
        this.articul = articul;
        this.name = name;
        this.price = price;
    }

    public Product(Long articul, String name, int price, LocalDate updatedTime) {
        this.articul = articul;
        this.name = name;
        this.price = price;
        this.updatedTime = updatedTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDate updatedTime) {
        this.updatedTime = updatedTime;
    }
}
