package com.alex.vis.swapi_dev_es_test_task.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlanetSearchResult {
    private Integer count;
    private String next;
    private String previous;
    private Planet[] results;
}
