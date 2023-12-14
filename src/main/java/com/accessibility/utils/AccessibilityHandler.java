package com.accessibility.utils;

import com.accessibility.utils.UsefulBoolean;
import org.openqa.selenium.WebDriver;

public interface AccessibilityHandler {
    void checkCurrentPage(WebDriver driver, String scenario);
    UsefulBoolean isAccessible(WebDriver driver, String scenario);
    void generateReport(String fileName);
}
