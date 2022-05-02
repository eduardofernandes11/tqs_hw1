package com.example.cache;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;
import com.example.coviddata.cache.CityCache;
import com.example.coviddata.cache.CountryCache;
import com.example.coviddata.model.City;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;
import java.util.List;

public class CacheTests {
    
    private CityCache cityCache;
    private CountryCache countryCache;

    @BeforeEach
    public void startUp() throws InterruptedException{
        this.cityCache = new CityCache(1);
        this.countryCache = new CountryCache(1);
    }

    @Test
    public void addValueToCacheTest(){
        //City Cache
        assertEquals(0, this.cityCache.getCacheSize());
        this.cityCache.addValue("CityTest", new City("CityTest"));
        assertEquals(1, this.cityCache.getCacheSize());
        assertEquals(true, this.cityCache.containsCity("CityTest"));
        // Country cache
        assertEquals(0, this.countryCache.getCacheSize());
        this.countryCache.addValue("CountryTest", new ArrayList<>());
        assertEquals(1, this.countryCache.getCacheSize());
        assertEquals(true, this.countryCache.containsCity("CountryTest"));
    }

    @Test
    public void cleanAfterTimeTest() throws InterruptedException{
        //City Cache
        City city = new City("Aveiro");
        this.cityCache.addValue("Aveiro", city);
        this.cityCache.cacheTimer("Aveiro", 10);
        Thread.sleep(2000);
        assertEquals(0, this.cityCache.getCacheSize());
        //Country cache
        ArrayList country = new ArrayList<>();
        this.countryCache.addValue("PRT", country);
        this.countryCache.cacheTimer("PRT", 10);
        Thread.sleep(2000);
        assertEquals(0, this.countryCache.getCacheSize());
    }

    @Test
    public void hitsMissesAndRequestsTest(){
        //City cache
        City city = new City("Paris");
        this.cityCache.addValue("Paris", city);
        this.cityCache.getCityFromCache("Paris");
        this.cityCache.getCityFromCache("Paris");
        this.cityCache.getCityFromCache("null");
        assertEquals(3, this.cityCache.getRequests());
        assertEquals(2, this.cityCache.getHits());
        assertEquals(1, this.cityCache.getMisses());
        //Couuntry cache
        ArrayList country = new ArrayList<>();
        this.countryCache.addValue("FR", country);
        this.countryCache.getArrayFromCache("FR");
        this.countryCache.getArrayFromCache("FR");
        this.countryCache.getArrayFromCache("null");
        assertEquals(3, this.cityCache.getRequests());
        assertEquals(2, this.cityCache.getHits());
        assertEquals(1, this.cityCache.getMisses());
    }

    @AfterEach
    public void tearDown(){
        this.cityCache.clearCache();
        this.countryCache.clearCache();
    }
}
