package com.alex.vis.swapi_dev_es_test_task.client;

import com.alex.vis.swapi_dev_es_test_task.domain.Planet;
import com.alex.vis.swapi_dev_es_test_task.domain.PlanetSearchResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="${swapi.dev.api.name}", url="${swapi.dev.api.url}")
public interface SwapiDevFeignClient {

    @GetMapping("${swapi.dev.api.planets}")
    public PlanetSearchResult getAllSwapiPlanets();

}
//

//public interface CurrencyRatesServiceProxy {

//
//    @GetMapping("${currency.rates.by.date}")
//    public CurrencyRatesBean retrieveCurrencyRatesByDate(@PathVariable String date);
//
//}