package com.accessibility.utils;

import java.util.HashMap;
import java.util.Map;

class HttpHelper {
    protected static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
