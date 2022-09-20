package com.alex.vis.swapi_dev_es_test_task.controller;

import com.alex.vis.swapi_dev_es_test_task.domain.Planet;
import com.alex.vis.swapi_dev_es_test_task.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/planets")
@RequiredArgsConstructor
public class PlanetController {
    private final PlanetService planetService;

    @GetMapping("/{name}")
    public Planet findByName(@PathVariable String name) {
        return planetService.findByName(name);
    }

    @PostMapping
    public void save(@RequestBody final Planet planet) {
        planetService.save(planet);
    }

    @GetMapping("/hint")
    public List<String> getHint(@RequestParam(name = "name") String searchString) throws IOException {
        return planetService.getHint(searchString);
    }
}
