package com.alex.vis.swapi_dev_es_test_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.alex.vis.swapi_dev_es_test_task.client")
public class SwapiDevEsTestTaskApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SwapiDevEsTestTaskApplication.class, args);
    }

}

//FileUtil.downloadFile(new URL(
//        "https://swapi.dev/api/planets?format=json"),
//        "E:\\Projects\\swapi_dev_es_test_task\\planets.json"
//        );
//        try {
//             json = new JSONObject(jsonString);
//            int someInt = json.getInt("someInt");
//            String someString = json.getString("someString");
//        } catch (JSONException e) {
//            //
//        }