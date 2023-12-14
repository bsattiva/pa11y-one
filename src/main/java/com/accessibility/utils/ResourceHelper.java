package com.accessibility.utils;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

class ResourceHelper {

    protected static String getResourcePath(final String fileName, final boolean main) {
        var branch = (main) ? "main" : "test";
        return System.getProperty("user.dir")
                + File.separator
                + "src"
                + File.separator
                + branch
                + File.separator
                + "resources"
                + File.separator
                + fileName;

    }


    public static Document getHtmlFromLocalResources(String fileName) {
        ClassLoader classLoader = ResourceHelper.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            if (inputStream != null) {
                try (Scanner scanner = new Scanner(inputStream).useDelimiter("\\A")) {
                    return Jsoup.parse(scanner.hasNext() ? scanner.next() : "");
                }
            } else {
                throw new RuntimeException("Resource not found: " + fileName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading resource: " + fileName, e);
        }
    }


}
