package com.example.coviddata.service;

import com.example.coviddata.model.City;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

public interface CountryService {
    City getCityByName(String name) throws IOException, URISyntaxException;
    HashMap<String, String> getCountries() throws IOException, URISyntaxException;
    ArrayList<String> getCities(String iso) throws IOException, URISyntaxException;
    HashMap<String, HashMap<String, Integer>> getCacheDetails();
}
