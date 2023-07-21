package kz.inflation.InflationApp.models.pharmacy;

import jakarta.persistence.*;
import kz.inflation.InflationApp.models.abstractClasses.ItemCategoryAbstract;

import java.util.List;

@Entity(name = "pharmacy_category")
public class PharmacyCategory extends ItemCategoryAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany( fetch = FetchType.EAGER ,mappedBy = "category", cascade = CascadeType.ALL)
    private List<Pharmacy> pharmacies;


    public PharmacyCategory(String name) {
        this.name = name;
    }

    public PharmacyCategory() {

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

    public List<Pharmacy> getPharmacies() {
        return pharmacies;
    }

    public void setPharmacies(List<Pharmacy> pharmacies) {
        this.pharmacies = pharmacies;
    }

    @Override
    public String toString() {
        return "PharmacyCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pharmacies=" + pharmacies +
                '}';
    }
}
