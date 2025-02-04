package com.Musicom.web.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BandAddFormTest {
    private WebDriver webDriver;

    @BeforeEach
    public void setUp() {
        webDriver = new ChromeDriver();
    }

    @Test
    public void testAddFormPage() {
        String name = "Test_band_name";
        String url = "https://translate.google.pl/?hl=uk&sl=pl&tl=uk&op=translate";
        int popularity = 33;
        String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4d/Cat_November_2010-1a.jpg/1200px-Cat_November_2010-1a.jpg";
        boolean result = new BandAddFormPage(webDriver)
                .open()
                .fillInNameInput(name)
                .fillInUrlInput(url)
                .fillInPopularityInput(popularity)
                .fillInImageInput(imageUrl)
                .submitForm()
                .isVisible(name, url, popularity, imageUrl);
        webDriver.close();
        assertTrue(result);
    }
}
