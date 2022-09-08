package com.alex.vis.swapi_dev_es_test_task.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
//TODO Fix field names to snake convention.
// Use https://stackoverflow.com/questions/69682344/how-do-i-parse-snake-case-fields-in-a-feignclient-response-json
public class Planet {
    private String name;
    private String rotation_period;
    private String orbital_period;
    private String diameter;
    private String climate;
    private String gravity;
    private String terrain;
    private String surface_water;
    private String population;
    private String[] residents;
    private String[] films;
    private Date created;
    private Date edited;
    private String url;
}
