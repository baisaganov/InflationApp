package kz.inflation.InflationApp.models.householdGoods;

import jakarta.persistence.*;
import kz.inflation.InflationApp.models.abstractClasses.ItemCategoryAbstract;
import kz.inflation.InflationApp.models.pharmacy.Pharmacy;

import java.util.List;
import java.util.Objects;

@Entity(name = "household_goods_category")
public class HouseholdGoodsCategory extends ItemCategoryAbstract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany( fetch = FetchType.EAGER ,mappedBy = "category", cascade = CascadeType.ALL)
    private List<HouseholdGoods> householdGoodsList;


    public HouseholdGoodsCategory() {
    }

    public HouseholdGoodsCategory(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseholdGoodsCategory that = (HouseholdGoodsCategory) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "HouseholdGoodsCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
