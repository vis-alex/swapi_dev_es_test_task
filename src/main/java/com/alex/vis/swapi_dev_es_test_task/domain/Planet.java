package com.alex.vis.swapi_dev_es_test_task.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.Date;

@Data
@NoArgsConstructor
//TODO Fix field names to snake convention.
// Use https://stackoverflow.com/questions/69682344/how-do-i-parse-snake-case-fields-in-a-feignclient-response-json
@Document(indexName = "planets")
@Setting(settingPath = "/static/settings/es-settings.json")
public class Planet {
    @Id
    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Text)
    private String rotation_period;
    @Field(type = FieldType.Text)
    private String orbital_period;
    @Field(type = FieldType.Text)
    private String diameter;
    @Field(type = FieldType.Text)
    private String climate;
    @Field(type = FieldType.Text)
    private String gravity;
    @Field(type = FieldType.Text)
    private String terrain;
    @Field(type = FieldType.Text)
    private String surface_water;
    @Field(type = FieldType.Text)
    private String population;
    @Field(type = FieldType.Auto)
    private String[] residents;
    @Field(type = FieldType.Auto)
    private String[] films;
    @Field(type = FieldType.Date)
    private Date created;
    @Field(type = FieldType.Date)
    private Date edited;
    @Field(type = FieldType.Text)
    private String url;
}

