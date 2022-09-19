package com.alex.vis.swapi_dev_es_test_task.service;

import com.alex.vis.swapi_dev_es_test_task.domain.Planet;
import com.alex.vis.swapi_dev_es_test_task.helper.InitialDownloading;
import com.alex.vis.swapi_dev_es_test_task.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PlanetService {
    private final PlanetRepository repository;

    @PostConstruct
    public void downloadDataToElasticIndex() {
        List<Planet> planets = new ArrayList<>();
        try {
            InitialDownloading.downloadData("https://swapi.dev/api/planets?format=json");
            InitialDownloading.extractPlanetsInfoToJson();
            planets = InitialDownloading.convertJsonToPlanets();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Planet planet : planets) {
            save(planet);
        }
    }

    public Planet findByName(String name) {
        //TODO Приделать нижний регистр в поиске
        return repository.findByName(name);
    }

    public void save(Planet planet) {
        repository.save(planet);
    }
}
