package com.example.coviddata.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CityTest {

    @Test
    public void tests() {
        City city = new City("Paris", 48.864716, 2.349014, 124154, 23145, 3412431, 423423, 0.0012);

        assertEquals("Paris", city.getName());
        assertEquals(48.864716, city.getLat());
        assertEquals(2.349014, city.getLon());
        assertEquals(124154, city.getConfirmed());
        assertEquals(23145, city.getDeaths());
        assertEquals(3412431, city.getRecovered());
        assertEquals(423423, city.getActive());
        assertEquals(0.0012, city.getFatalityRate());
    }
}
