package com.alex.vis.swapi_dev_es_test_task.service;

import com.alex.vis.swapi_dev_es_test_task.domain.Planet;
import com.alex.vis.swapi_dev_es_test_task.repository.PlanetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class PlanetService {
    private final String DOWNLOADED_FILES_DIRECTORY = "src/main/resources/downloaded_files";
    private int count = 0;
    private final JSONParser parser = new JSONParser();
    private boolean isDownloaded = false;

    private final PlanetRepository repository;

    @PostConstruct
    public void downloadDataToElasticIndex() {
        List<Planet> planets = new ArrayList<>();
        try {
            downloadData("https://swapi.dev/api/planets?format=json");
            extractPlanetsInfoToJson();
            planets = convertJsonToPlanets();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Planet planet : planets) {
            save(planet);
        }
    }

    private void downloadData(String url) {
        File planetsDirectory = new File(DOWNLOADED_FILES_DIRECTORY);
        String filePath = planetsDirectory.getPath() + "/" + (++count) + ".json";
        downloadFile(url,  filePath , false);
        downloadPlanets(getJsonObjectFromFile(filePath));
    }

    private  List<Planet> convertJsonToPlanets() throws Exception {
        ArrayList<Planet> planets = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        File directory = new File("src/main/resources/downloaded_files");

        while (true) {
            if (isDownloaded) {
                for (File file : Objects.requireNonNull(directory.listFiles())) {
                    JSONObject obj = getJsonObjectFromFile(file.getPath());

                    JSONArray jsonArray = (JSONArray) obj.get("results");

                    if (jsonArray != null) {
                        int len = jsonArray.size();
                        for (int i = 0; i < len; i++) {
                            Planet planet = mapper.readValue(jsonArray.get(i).toString(), Planet.class);
                            planets.add(planet);
                        }
                    }
                }
                break;
            }
        }

        for (int i = 0; i < planets.size(); i++) {
            System.out.println("Planet "  + (i + 1) + " :   " + planets.get(i));
        }

        return planets;
    }

    private void extractPlanetsInfoToJson() throws Exception {
        File planetsDirectory = new File(DOWNLOADED_FILES_DIRECTORY);
        File jsonPlanets = new File("src/main/resources/planets.json");

        if (jsonPlanets.exists()) {
            jsonPlanets.delete();
        }

        StringBuilder jsonResult = new StringBuilder();
        try (FileWriter fileWriter = new FileWriter(jsonPlanets, true)) {

            jsonResult.append("[");
            while (true) {
                if (isDownloaded) {
                    for (File file : planetsDirectory.listFiles()) {
                        JSONObject rootObject = getJsonObjectFromFile(file.getPath());
                        String results = rootObject.get("results").toString();
                        jsonResult.append(results, 1, results.length() - 1);
                        jsonResult.append(",");
                        System.out.println(results);
                    }
                    break;
                }
            }
            jsonResult.deleteCharAt(jsonResult.length() - 1);
            jsonResult.append("]");
            fileWriter.write(jsonResult.toString());
            fileWriter.flush();

            //TODO -----------------------------------
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void downloadPlanets(JSONObject rootJsonObject) {
        String nextPageUrl;
        if (rootJsonObject.get("next") != null) {
            nextPageUrl = rootJsonObject.get("next").toString();
            System.out.println(nextPageUrl);

            String fileName = DOWNLOADED_FILES_DIRECTORY + "/" + (++count) + ".json";

            downloadFile(
                    nextPageUrl,
                    fileName,
                    false
            );

            JSONObject nextPageJsonObject = getJsonObjectFromFile(fileName);
            downloadPlanets(nextPageJsonObject);
        }
        isDownloaded = true;
    }

    private  JSONObject getJsonObjectFromFile(String fileName) {

        try (FileReader reader = new FileReader(fileName)){
            return (JSONObject) parser.parse(reader);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void downloadFile(String urlPath, String fileName, boolean append) {
        try {
            URI url = new URI(urlPath);

            HttpURLConnection connection = (HttpURLConnection) url.toURL().openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


            try (FileWriter fileWriter = new FileWriter(fileName, append)) {
                fileWriter.write(response.toString());
                fileWriter.flush();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Planet findByName(String name) {
        //TODO Приделать нижний регистр в поиске
        return repository.findByName(name);
    }

    public void save(Planet planet) {
        repository.save(planet);
    }
}
