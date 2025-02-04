package com.Musicom.web.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class BandDeleteFormTest {
    private WebDriver webDriver;

    @BeforeEach
    public void setUp() {
        webDriver = new ChromeDriver();
    }

    @Test
    public void testBandDeleteForm() {
        int id = 1104;
        boolean result = new BandDeleteFormPage(webDriver)
                .open()
                .fillInIdInput(id)
                .submitForm()
                .isVisible(id);
        assertFalse(result);
        webDriver.close();
    }
}
