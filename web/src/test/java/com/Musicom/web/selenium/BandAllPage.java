package com.Musicom.web.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BandAllPage {
    private final WebDriver webDriver;

    public BandAllPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public boolean isVisible(String name, String url, int popularity, String image) {
        try {
            WebElement foundName = webDriver.findElement(By.linkText(name));
            String foundUrl = foundName.getAttribute("href");
            WebElement foundPopularity = webDriver.findElement(By.xpath("//*[text()='" + popularity + "']"));
            String foundImageUrl = webDriver.findElement(By.tagName("img")).getAttribute("src");
            return foundName.isDisplayed()
                    && url.equals(foundUrl)
                    && foundPopularity.isDisplayed()
                    && image.equals(foundImageUrl);
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public boolean isVisible(int id, String name, String url, int popularity, String image) {
        try {
            WebElement foundBand = webDriver.findElement(By.id("band-" + id));
            WebElement foundName = webDriver.findElement(By.linkText(name));
            String foundUrl = foundName.getAttribute("href");
            WebElement foundPopularity = webDriver.findElement(By.xpath("//*[text()='" + popularity + "']"));
            String foundImageUrl = webDriver.findElement(By.tagName("img")).getAttribute("src");
            return foundBand.isDisplayed()
                    && foundName.isDisplayed()
                    && url.equals(foundUrl)
                    && foundPopularity.isDisplayed()
                    && image.equals(foundImageUrl);
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public boolean isVisible(int id) {
        try {
            WebElement foundBand = webDriver.findElement(By.id("band-" + id));
            return foundBand.isDisplayed();
        } catch (NoSuchElementException ex) {
            return false;
        }
    }
}
