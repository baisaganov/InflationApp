package kz.inflation.InflationApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity(name = "mrp")
@Table(indexes = {
        @Index(name = "idx_mrp_year_unq", columnList = "year", unique = true)
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"year", "id"})
})
public class MRP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "year", nullable = false, unique = true)
    private Integer year;

    @Column(name = "value")
    private Integer value;

    public MRP() {
    }

    public MRP(Integer year, Integer value) {
        this.year = year;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
