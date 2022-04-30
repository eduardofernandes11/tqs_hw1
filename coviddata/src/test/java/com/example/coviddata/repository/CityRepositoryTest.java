package com.example.coviddata.repository;

import com.example.coviddata.model.City;
import com.example.coviddata.repository.CityRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
public class CityRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void findByNameTest() {
        City aveiro = new City("Aveiro");
        testEntityManager.persistAndFlush(aveiro);

        City city = cityRepository.findByName(aveiro.getName());
        assertThat(city).isEqualTo(aveiro);
    }

    @Test
    public void findAllTest() {
        City aveiro = new City("Aveiro");
        City lisboa = new City("Lisboa");
        City porto = new City("Porto");
        City guarda = new City("Guarda");
        testEntityManager.persist(aveiro);
        testEntityManager.persist(lisboa);
        testEntityManager.persist(porto);
        testEntityManager.persist(guarda);
        testEntityManager.flush();

        List<City> cities = cityRepository.findAll();
        assertThat(cities).hasSize(4).extracting(City::getName).contains(aveiro.getName(), lisboa.getName(), porto.getName(), guarda.getName());
    }

}
