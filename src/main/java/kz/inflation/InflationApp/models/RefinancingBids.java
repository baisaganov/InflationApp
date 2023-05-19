package kz.inflation.InflationApp.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "refinancing_bids")
public class RefinancingBids {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "year", unique = true)
    private LocalDate year;

    @Column(name = "percent")
    private Float percent;

    public RefinancingBids() {
    }

    public RefinancingBids(LocalDate year, Float percent) {
        this.year = year;
        this.percent = percent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }
}
