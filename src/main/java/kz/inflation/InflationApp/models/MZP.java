package kz.inflation.InflationApp.models;

import jakarta.persistence.*;

@Entity(name = "mzp")
public class MZP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "year", unique = true)
    private Integer year;

    @Column(name = "value")
    private Integer value;

    public MZP() {
    }

    public MZP(Integer year, Integer value) {
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
