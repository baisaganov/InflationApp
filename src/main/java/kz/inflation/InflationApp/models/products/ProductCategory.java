package kz.inflation.InflationApp.models.products;

import jakarta.persistence.*;
import kz.inflation.InflationApp.models.abstractClasses.ItemCategoryAbstract;
import kz.inflation.InflationApp.models.products.Product;

import java.util.List;
import java.util.Objects;

@Entity(name = "product_category")
public class ProductCategory extends ItemCategoryAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany( fetch = FetchType.EAGER ,mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

    public ProductCategory() {
    }

    public ProductCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", product=" + products +
                '}';
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategory that = (ProductCategory) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
