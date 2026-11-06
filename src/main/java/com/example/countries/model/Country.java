package com.example.countries.model;

import jakarta.persistence.*;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String capital;

    @Column(nullable = false)
    private String region;

    @Column(name = "sub_region", nullable = false)
    private String subRegion;

    @Column(nullable = false)
    private Double area;

    protected Country() {
    }

    public Country(String name, String capital, String region, String subRegion, Double area) {
        this.name = name;
        this.capital = capital;
        this.region = region;
        this.subRegion = subRegion;
        this.area = area;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCapital() { return capital; }
    public String getRegion() { return region; }
    public String getSubRegion() { return subRegion; }
    public Double getArea() { return area; }

    public void setName(String name) { this.name = name; }
    public void setCapital(String capital) { this.capital = capital; }
    public void setRegion(String region) { this.region = region; }
    public void setSubRegion(String subRegion) { this.subRegion = subRegion; }
    public void setArea(Double area) { this.area = area; }
}
