package com.Musicom.spotify_client.provider;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

@Component
public class WebDriverProvider {
    public WebDriver getWebDriver() {
        return new ChromeDriver();
    }
}
