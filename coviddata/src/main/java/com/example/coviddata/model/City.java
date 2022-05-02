package com.example.coviddata.model;

import javax.persistence.*;

@Entity
@Table(name= "city")
public class City {
    
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Double lat;
    private Double lon;
    private int confirmed;
    private int deaths;
    private int recovered;
    private int active;
    private double fatalityRate;

	public City(String name){
		this.name = name;
	}

    public City(String name, Double lat, Double lon, int confirmed, int deaths, int recovered, int active, Double fatality_rate){
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered =recovered;
        this.active = active;
        this.fatalityRate = fatality_rate;
    }

    public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLat() {
		return this.lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return this.lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public int getConfirmed() {
		return this.confirmed;
	}

	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}

	public int getDeaths() {
		return this.deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getRecovered() {
		return this.recovered;
	}

	public void setRecovered(int recovered) {
		this.recovered = recovered;
	}

	public int getActive() {
		return this.active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public double getFatalityRate() {
		return this.fatalityRate;
	}

	public void setFatalityRate(double fatalityRate) {
		this.fatalityRate = fatalityRate;
	}


}
