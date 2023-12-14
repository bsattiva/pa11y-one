package com.accessibility.utils;


import org.json.JSONObject;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

public class Pa11yDriver implements DriverSteps {
    private final String url;

    public Pa11yDriver(final String url) {
        this.url = url;
        Steps.unload();
    }

    @Override
    public void navigate(final String url) {
        Steps.addStep(new Step("", url, 0, "navigate"));
    }

    @Override
    public void click(final String selector, final int timeout) {
        Steps.addStep(new Step(selector, "", timeout, "click"));
    }

    @Override
    public void sendKeys(final String selector, final String keys, final int timeout) {
        Steps.addStep(new Step(selector, keys, timeout, "type"));
    }

    @Override
    public void wait(final int mills) {
        Steps.addStep(new Step("", "", mills, "wait"));
    }

    @Override
    public void waitClickable(final String selector, final int mills) {
        Steps.addStep(new Step(selector, "", mills, "waitFor"));
    }

    @Override
    public void testAccessibility(final String hostname, final String port, final String scenario) {
        var backend = hostname + ":" + port + "/run-accessibility-test";
        final List<HttpCookie> cookies = new ArrayList<>();
        var body = new JSONObject() {{
          put("url", url);
          put("actions", Steps.getSteps());
        }};
        var status = HttpClient.sendHttpPost(body, backend, cookies, HttpHelper.getHeaders());
        var object = new JSONObject(status.getString("message"));
        object.put("scenario", scenario);
        Issues.add(object);
    }


}
