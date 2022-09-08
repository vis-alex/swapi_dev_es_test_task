package com.alex.vis.swapi_dev_es_test_task.util;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public final class FileUtil {

    private FileUtil() {}

    /**If you don`t need to replace file with restart the application
     - deleteStandardCopyOption.REPLACE_EXISTING parameter */
    public static void downloadFile(URL url, String fileName) throws Exception {
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
