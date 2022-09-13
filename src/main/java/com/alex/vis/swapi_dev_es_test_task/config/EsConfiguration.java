package com.alex.vis.swapi_dev_es_test_task.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestHighLevelClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@org.springframework.context.annotation.Configuration
@EnableElasticsearchRepositories(basePackages = "com.alex.vis.swapi_dev_es_test_task.repository")
@ComponentScan(basePackages = "com.alex.vis.swapi_dev_es_test_task")
public class EsConfiguration extends AbstractElasticsearchConfiguration {

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        return new RestHighLevelClientBuilder(
                RestClient.builder(
                                new HttpHost("localhost", 9200, "http"))
                        .build())
                .setApiCompatibilityMode(true)
                .build();
    }
}
