package com.accessibility.utils;

import lombok.Getter;
import org.json.JSONObject;

class Step {
    @Getter
    private final String selector;
    @Getter
    private final String value;
    @Getter
    private final int timeout;
    @Getter
    private final String action;

    protected Step(final String selector, final String value, final int timeout, final String action) {
        this.selector = selector;
        this.value = value;
        this.timeout = timeout;
        this.action = action;

    }

    protected JSONObject getStep() {
        return new JSONObject() {{
            put("selector", selector);
            put("value", value);
            put("timeout", timeout);
            put("action", action);
        }};

    }
}
