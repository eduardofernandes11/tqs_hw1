package com.example.coviddata.controller;

import com.example.coviddata.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.example.coviddata.service.CountryService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class CountryController {

    @Autowired
    CountryService countryService;

    @GetMapping("/city/{name}")
    public City getCityByName(@PathVariable(value= "name") String name) throws IOException, URISyntaxException {
        City city = countryService.getCityByName(name);
        if (city == null){
            return null;
        }
        else {
            return city;
        }
    }

    @GetMapping("/countries")
    public HashMap<String, String> getCountries() throws IOException, URISyntaxException {
        HashMap<String, String> values = countryService.getCountries();
        if (values==null){
            return null;
        }
        else{
            return values;
        }
    }

    @GetMapping("/cities/{iso}")
    public ArrayList<String> getCities(@PathVariable(value= "iso") String iso) throws IOException, URISyntaxException {
        ArrayList<String> cities = countryService.getCities(iso);
        if (cities==null){
            return null;
        }
        else{
            return cities;
        }
    }

    @GetMapping("/cacheDetails")
    public HashMap<String, HashMap<String, Integer>> getCityCacheDetails() {
        return countryService.getCacheDetails();
    }

}
