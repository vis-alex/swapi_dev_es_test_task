package com.alex.vis.swapi_dev_es_test_task.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;

public final class FileUtil {
    public static final String DOWNLOADED_FILES_DIRECTORY = "src/main/resources/downloaded_files";
    private static int count = 0;
    private static final JSONParser parser = new JSONParser();

    private FileUtil() {}

    public static void downloadData(String url) throws Exception {
        File planetsDirectory = new File(DOWNLOADED_FILES_DIRECTORY);
        String filePath = planetsDirectory.getPath() + "/" + (++count) + ".json";
        downloadFile(url,  filePath , false);
        downloadPlanets(FileUtil.getJsonObjectFromFile(filePath));
    }

    public static void downloadFile(String urlPath, String fileName, boolean append) throws Exception {
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


            try (FileWriter file = new FileWriter(fileName, append)) {
                file.write(response.toString());
                file.flush();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    //TODO THROW PARSE EXCEPTION
    public static JSONObject getJsonObjectFromFile(String fileName) {

        try (FileReader reader = new FileReader(fileName)){
            return (JSONObject) parser.parse(reader);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void downloadPlanets(JSONObject rootJsonObject) throws Exception {
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
    }

}
