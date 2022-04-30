package com.example.coviddata.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.example.coviddata.model.City;
import com.example.coviddata.repository.CityRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CountryServiceTest {

    @Mock(lenient=true)
    private CityRepository cityRepository;

    @InjectMocks
    private CountryServiceImpl countryService;

    @BeforeEach
    public void startUp(){
        City paris = new City("Paris");
        City aveiro = new City("Aveiro");
        City porto = new City("Porto");
        List<City> cities = new ArrayList<City>();
        cities.add(paris);
        cities.add(aveiro);
        cities.add(porto);

        Mockito.when(cityRepository.findByName(paris.getName())).thenReturn(paris);
        Mockito.when(cityRepository.findByName(aveiro.getName())).thenReturn(aveiro);
        Mockito.when(cityRepository.findByName(porto.getName())).thenReturn(porto);
        Mockito.when(cityRepository.findAll()).thenReturn(cities);
    }

    @Test
    public void cityTest() throws IOException, URISyntaxException {
        City city = countryService.getCityByName("Washington");
        assertThat(city.getName()).isEqualTo("Washington");
    }
    
    
}
