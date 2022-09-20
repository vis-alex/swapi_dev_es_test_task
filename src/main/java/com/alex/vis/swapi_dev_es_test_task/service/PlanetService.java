package com.alex.vis.swapi_dev_es_test_task.service;

import com.alex.vis.swapi_dev_es_test_task.domain.Planet;
import com.alex.vis.swapi_dev_es_test_task.helper.InitialDownloading;
import com.alex.vis.swapi_dev_es_test_task.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.spatial3d.geom.Plane;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class PlanetService {
    private final PlanetRepository repository;
    private final RestHighLevelClient esClient;
    private final static String INDEX_NAME = "planets";

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

    public List<String> getHint(String searchString) throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.matchQuery(searchString,"name")
                .fuzziness(2));

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);


        List<String> planetNames = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");

            planetNames.add(name);
        }

        return planetNames;
    }
}
