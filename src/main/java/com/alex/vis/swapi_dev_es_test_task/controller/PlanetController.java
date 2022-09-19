package com.alex.vis.swapi_dev_es_test_task.controller;

import com.alex.vis.swapi_dev_es_test_task.domain.Planet;
import com.alex.vis.swapi_dev_es_test_task.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


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
}
