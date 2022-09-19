package com.alex.vis.swapi_dev_es_test_task.helper;

import com.alex.vis.swapi_dev_es_test_task.domain.Planet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InitialDownloading {
    private static final String DOWNLOADED_FILES_DIRECTORY = "src/main/resources/downloaded_files";
    private static int count = 0;
    private static  final JSONParser parser = new JSONParser();
    private static boolean isDownloaded = false;


    public static void downloadData(String url) {
        File planetsDirectory = new File(DOWNLOADED_FILES_DIRECTORY);
        String filePath = planetsDirectory.getPath() + "/" + (++count) + ".json";
        downloadFile(url,  filePath , false);
        downloadPlanets(getJsonObjectFromFile(filePath));
    }

    public static List<Planet> convertJsonToPlanets() throws Exception {
        ArrayList<Planet> planets = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        File directory = new File(DOWNLOADED_FILES_DIRECTORY);

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

    public static void extractPlanetsInfoToJson() throws Exception {
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

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static void downloadPlanets(JSONObject rootJsonObject) {
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

    private static  JSONObject getJsonObjectFromFile(String fileName) {

        try (FileReader reader = new FileReader(fileName)){
            return (JSONObject) parser.parse(reader);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void downloadFile(String urlPath, String fileName, boolean append) {
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

}
