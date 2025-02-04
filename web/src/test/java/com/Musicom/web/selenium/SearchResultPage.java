package com.Musicom.web.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchResultPage {
    private final WebDriver webDriver;

    public SearchResultPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public boolean isVisible(String name) {
        try {
            WebElement webElement = webDriver.findElement(By.linkText(name));
            return webElement.isDisplayed();
        } catch (NoSuchElementException ex) {
            return false;
        }
    }
}
