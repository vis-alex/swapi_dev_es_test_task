package com.alex.vis.swapi_dev_es_test_task;

import com.alex.vis.swapi_dev_es_test_task.util.FileUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.io.File;
import java.nio.file.Files;

@SpringBootApplication
@EnableFeignClients("com.alex.vis.swapi_dev_es_test_task.client")
public class SwapiDevEsTestTaskApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SwapiDevEsTestTaskApplication.class, args);

        FileUtil.downloadData("https://swapi.dev/api/planets?format=json");


//        FileUtil.downloadFile(
//                "https://swapi.dev/api/planets?format=json",
//                "planets.json",
//                false);
//        FileUtil.writePlanetsAsJsonToFile(FileUtil.getJsonObjectFromFile("planets.json"));
//
//
//        long result = FileUtil.getResultCount("planets.json");
//        System.out.println(result);
//
//        if (result > 10) {
//            long countPages = (long)Math.ceil(result / 10);
//            System.out.println("countPages - " + countPages);
//
//        }



    }
}