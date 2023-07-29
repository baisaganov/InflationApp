package kz.inflation.InflationApp.models.products;

import jakarta.persistence.*;
import kz.inflation.InflationApp.models.abstractClasses.ItemAbstract;

import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "products")
public class Product extends ItemAbstract {
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

    @ManyToOne()
    @JoinColumn(name = "cat_id", referencedColumnName = "id")
    private ProductCategory category;

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

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(articul, product.articul);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articul);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", articul=" + articul +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", updatedTime=" + updatedTime +
                ", category=" + category.getName() +
                '}';
    }
}
