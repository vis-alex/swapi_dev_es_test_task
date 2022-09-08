package com.alex.vis.swapi_dev_es_test_task.service;

import com.alex.vis.swapi_dev_es_test_task.client.SwapiDevFeignClient;
import com.alex.vis.swapi_dev_es_test_task.domain.Planet;
import com.alex.vis.swapi_dev_es_test_task.domain.PlanetSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PlanetService {

    private final SwapiDevFeignClient client;

    public Planet[] getAllPlanets() {
        PlanetSearchResult result = client.getAllSwapiPlanets();
        return result.getResults();
    }
}
