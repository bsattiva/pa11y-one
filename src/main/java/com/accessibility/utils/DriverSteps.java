package com.accessibility.utils;

public interface DriverSteps {
    void navigate(String url);
    void click(String selector, int timeout);
    void sendKeys(String selector, String keys, int timeout);
    void wait(int mills);
    void waitClickable(String selecror, int mills);
    void testAccessibility(String hostnam, String port, String scenario);
}
