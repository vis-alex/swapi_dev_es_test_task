package com.alex.vis.swapi_dev_es_test_task.repository;

import com.alex.vis.swapi_dev_es_test_task.domain.Planet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PlanetRepository extends ElasticsearchRepository<Planet, String> {
    Planet findByName(String name);
}
