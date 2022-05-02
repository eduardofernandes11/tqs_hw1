package com.example.coviddata.controller;

import com.example.coviddata.model.City;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.example.coviddata.service.CountryService;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.HashMap;

@WebMvcTest(CountryController.class)
public class CountryControllerTest {

    @MockBean
    private CountryService countryService;

    @Autowired
    private MockMvc mockmvc;

    @Test
    public void getCityByNameTest() throws Exception{
        City paris = new City("Paris", 48.864716, 2.349014, 124154, 23145, 3412431, 423423, 0.0012);

        when(countryService.getCityByName("Paris")).thenReturn(paris);
        mockmvc.perform(get("/city/{name}", "Paris").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.name", is("Paris")));
    }

    @Test
    public void getCountriesTest() throws Exception{
        HashMap<String, String> values = new HashMap<>();
        values.put("Portugal", "PRT");
        values.put("China", "CHN");

        when(countryService.getCountries()).thenReturn(values);
        mockmvc.perform(get("/countries").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.Portugal", is("PRT"))).andExpect(jsonPath("$.China", is("CHN")));
    }

    @Test
    public void getCitiesTest() throws Exception{
        ArrayList<String> values = new ArrayList<>();
        values.add("Porto");
        values.add("Aveiro");

        when(countryService.getCities("PRT")).thenReturn(values);
        mockmvc.perform(get("/cities/{iso}", "PRT").contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$[0]", is("Porto"))).andExpect(jsonPath("$[1]", is("Aveiro")));

    }

    @Test
    void getCacheDetailsTest() throws Exception {
        HashMap<String, HashMap<String, Integer>> cacheMap = new HashMap<>();
        HashMap<String, Integer> city = new HashMap<>();
        city.put("hits", 2);
        city.put("misses", 1);
        city.put("requests", 3);
        HashMap<String, Integer> country = new HashMap<>();
        country.put("hits", 1);
        country.put("misses", 2);
        country.put("requests", 3);
        cacheMap.put("CityCache", city);
        cacheMap.put("CountryCache", country);

        when(countryService.getCacheDetails()).thenReturn(cacheMap);
        mockmvc.perform(get("/cacheDetails").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.CityCache", is(city)))
                .andExpect(jsonPath("$.CountryCache", is(country)));

    }
    
}
