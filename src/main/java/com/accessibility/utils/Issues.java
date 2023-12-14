package com.accessibility.utils;


import org.apache.commons.io.FileUtils;

import org.json.JSONObject;
import org.jsoup.nodes.Document;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Issues {
    private static List<JSONObject> issues;

    private static void init() {
        if (issues == null)
        issues = new ArrayList<>();
    }

    protected static void add(JSONObject object) {
        init();
        issues.add(object);
    }

    public static void generateReport(final String filePath) {
        var file = ResourceHelper.getHtmlFromLocalResources("report.html");
        org.jsoup.nodes.Element scenario = file.getElementById("scenario").clone();
        org.jsoup.nodes.Element item = file.getElementById("item").clone();

        file.getElementById("container").getElementById("item").remove();
        file.getElementById("scenario").remove();
        for (var scenarioItem : issues) {
            var scena = scenario.clone();
            scena.getElementById("item").remove();
            scena.getElementById("title").text(scenarioItem.getString("documentTitle") + ": scenario: "
                    + scenarioItem.getString("scenario"));
            scena.getElementById("counter").text(Integer.toString(scenarioItem.getJSONArray("issues").length()));

            for (var i = 0; i < scenarioItem.getJSONArray("issues").length(); i++) {
                var issue = scenarioItem.getJSONArray("issues").getJSONObject(i);
                var localItem = item.clone();
                populateItem(localItem, issue);
                scena.appendChild(localItem);
            }
            file.getElementById("container").appendChild(scena);
        }

        saveHtml(filePath, file);

    }

    private static void saveHtml(final String filePath, final Document document) {
        var file = new File(filePath);
        try {
            FileUtils.write(file, document.toString(), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void populateItem(final org.jsoup.nodes.Element item, final JSONObject object) {
        item.getElementById("code").text(object.getString("code"));
        item.getElementById("type").text(object.getString("type"));
        item.getElementById("typeCode").text(Integer.toString(object.getInt("typeCode")));
        item.getElementById("message").text(object.getString("message"));
        item.getElementById("context").text(object.getString("context"));
        item.getElementById("selector").text(object.getString("selector"));
        item.getElementById("runner").text(object.getString("runner"));
        item.getElementById("runnerExtras").text(object.getJSONObject("runnerExtras").toString());
    }


}
