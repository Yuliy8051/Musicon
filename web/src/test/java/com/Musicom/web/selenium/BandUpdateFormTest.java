package com.Musicom.web.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BandUpdateFormTest {
    private WebDriver webDriver;

    @BeforeEach
    public void setUp() {
        webDriver = new ChromeDriver();
    }

    @Test
    public void testAddFormPage() {
        int id = 1104;
        String name = "Test_band_name_updated";
        String url = "https://dictionary.cabridge.org/";
        int popularity = 33;
        String imageUrl = "https://t4.ftcdn.net/jpg/02/66/72/41/360_F_266724172_Iy8gdKgMa7XmrhYYxLCxyhx6J7070Pr8.jpg";
        boolean result = new BandUpdateFormPage(webDriver)
                .open()
                .fillInIdInput(id)
                .fillInNameInput(name)
                .fillInUrlInput(url)
                .fillInPopularityInput(popularity)
                .fillInImageInput(imageUrl)
                .submitForm()
                .isVisible(id, name, url, popularity, imageUrl);
        webDriver.close();
        assertTrue(result);
    }
}
