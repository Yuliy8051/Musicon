package com.Musicom.web.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BandSearchFormTest {
    private WebDriver webDriver;

    @BeforeEach
    public void setUp() {
        webDriver = new ChromeDriver();
    }

    @Test
    public void TestBandSearchForm() {
        String name = "Mowy24";
        boolean result = new BandSearchFormPage(webDriver)
                .open()
                .fillInNameInput(name)
                .submitForm()
                .isVisible(name);
        assertTrue(result);
        webDriver.close();
    }
}
