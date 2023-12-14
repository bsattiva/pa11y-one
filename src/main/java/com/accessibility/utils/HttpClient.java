package com.accessibility.utils;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 class HttpClient {

    public static final int WAIT = 1000;
    private static final String MESSAGE = "message";
    public static final int RETRIES = 5;

    protected static JSONObject sendHttpPost(final JSONObject body,
                                          final String url,
                                          final List<HttpCookie> cookies,
                                          final Map<String, String> headers) {
        var obj = new JSONObject();
        try {
            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);
            cookies.forEach(cookie -> {
                try {
                    cookieManager.getCookieStore().add(new URI(url), cookie);
                } catch (URISyntaxException e) {
                   e.printStackTrace();
                }
            });
            var conn
                    = (url.contains("https"))
                    ? getHttpsConnection(body, url, headers, "POST")
                    : getHttpConnection(body, url, headers, "POST");

            obj = getObjectFromConnection(conn, RETRIES);


        } catch (Exception e) {
           e.printStackTrace();
        }
        return obj;
    }

    protected static JSONObject sendHttpPost(final JSONObject body, final String url, final List<HttpCookie> cookies) {
        var obj = new JSONObject();
        try {
            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);
            cookies.forEach(cookie -> {
                try {
                    cookieManager.getCookieStore().add(new URI(url), cookie);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            });
            var conn
                    = (url.contains("https"))
                    ? getHttpsConnection(body, url, new HashMap<>(), "POST")
                    : getHttpConnection(body, url, new HashMap<>(), "POST");

            obj = getObjectFromConnection(conn, RETRIES);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    private static HttpsURLConnection getHttpsConnection(final JSONObject body,
                                                         final String url,
                                                         final Map<String, String> headers,
                                                         final String method)
            throws IOException {

        var r = new URL(url);

        HttpsURLConnection conn = (HttpsURLConnection) r.openConnection();

        conn.setAllowUserInteraction(true);
        conn.setDoOutput(true);

        conn.setRequestMethod(method.toUpperCase());
        headers.forEach(conn :: setRequestProperty);
        var os = conn.getOutputStream();
        var osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
        if (body != null && body.length() > 0) {
            osw.write(body.toString());
        }


        osw.flush();
        osw.close();
        os.close();
        return conn;
    }

    private static HttpURLConnection getHttpConnection(final JSONObject body,
                                                        final String url,
                                                        final Map<String, String> headers,
                                                        final String method)
            throws IOException {

        var r = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) r.openConnection();

        conn.setAllowUserInteraction(true);
        conn.setDoOutput(true);

        conn.setRequestMethod(method.toUpperCase());
        headers.forEach(conn :: setRequestProperty);
        var os = conn.getOutputStream();
        var osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
        if (body != null && body.length() > 0) {
            osw.write(body.toString());
        }


        osw.flush();
        osw.close();
        os.close();
        return conn;
    }

    private static JSONObject getObjectFromConnection(final HttpURLConnection connection, final int retries) {
        var object = new JSONObject();
        for (var i = 0; i < retries; i++) {
            var gotObject = getObjectFromConnection(connection);

            if (gotObject.length() > 0) {
                object = gotObject;
                break;
            } else {
                TestHelper.sleep(WAIT);
            }
        }
        return object;
    }

    private static JSONObject getObjectFromInputStream(final InputStream is) throws IOException {
        var isr = new InputStreamReader(is);
        var br = new BufferedReader(isr);
        var response = new StringBuilder();
        var object = new JSONObject();

        String line;

        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        object.put(MESSAGE, response.toString());
        return object;

    }

    private static JSONObject getObjectFromConnection(final HttpURLConnection connection) {
        var object = new JSONObject();
        try {
            object = getObjectFromInputStream(connection.getInputStream());

        } catch (IOException e) {
            try {
                Thread.sleep(WAIT);
            } catch (InterruptedException ex) {
                e.printStackTrace();
            }
        }
        return object;
    }


}
