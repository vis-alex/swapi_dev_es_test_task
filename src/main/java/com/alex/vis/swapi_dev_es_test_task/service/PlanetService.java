package com.alex.vis.swapi_dev_es_test_task.service;

import com.alex.vis.swapi_dev_es_test_task.domain.Planet;
import com.alex.vis.swapi_dev_es_test_task.repository.PlanetRepository;
import com.alex.vis.swapi_dev_es_test_task.util.FileUtil;
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
            FileUtil.downloadData("https://swapi.dev/api/planets?format=json");
            FileUtil.extractPlanetsInfoToJson();
            planets = FileUtil.convertJsonToPlanets();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Planet planet : planets) {
            save(planet);
        }
    }

    public Planet findByName(String name) {
        return repository.findByName(name);
    }

    public void save(Planet planet) {
        repository.save(planet);
    }
}
