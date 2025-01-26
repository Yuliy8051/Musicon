package com.Musicom.spotify_client;

import com.Musicom.spotify_client.provider.WebDriverProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class WebDriverProviderTest {
    private WebDriverProvider webDriverProvider;

    @BeforeEach
    public void setUp() {
        webDriverProvider = new WebDriverProvider();
    }

    @Test
    public void shouldReturnChromeDriver() {
        WebDriver webDriver = webDriverProvider.getWebDriver();
        assertInstanceOf(ChromeDriver.class, webDriver);
        webDriver.close();
    }
}
