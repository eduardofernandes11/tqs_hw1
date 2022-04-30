package com.example.coviddata.model;

import javax.persistence.*;

@Entity
@Table(name = "country")
public class Country {
    
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String iso;
    private Double latitude;
    private Double longitude;

    public Country() {

    }

    public Country(String name) {
        this.name = name;
    }

    public Country(String name, String iso, Double latitude, Double longitude) {
        this.name = name;
        this.iso = iso;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    

}
