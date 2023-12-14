package com.accessibility.utils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

class Steps {
    private static List<Step> steps;

    protected static void addStep(final Step step) {
        init();
        steps.add(step);
    }


    private static void init() {
        if (steps == null) {
            steps = new ArrayList<>();
        }
    }

    protected static JSONArray getSteps() {
        var array = new JSONArray();
        steps.forEach(step -> array.put(step.getStep()));
        return array;
    }

    public static void unload() {
        steps = new ArrayList<>();
    }
}
